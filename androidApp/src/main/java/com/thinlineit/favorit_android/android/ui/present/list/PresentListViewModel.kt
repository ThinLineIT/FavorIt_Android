package com.thinlineit.favorit_android.android.ui.present.list

import androidx.lifecycle.*
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Present
import com.thinlineit.favorit_android.android.ui.present.list.PresentAdapter.Companion.MAX_PRESENT_COUNT_EACH_PAGE
import com.thinlineit.favorit_android.android.ui.present.list.PresentListActivity.Companion.FUNDING_ID
import com.thinlineit.favorit_android.android.ui.present.usecase.GetPresentList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PresentListViewModel @Inject constructor(
    private val getPresentList: GetPresentList,
    state: SavedStateHandle
) : ViewModel(), AlbumPageAdapter.BookPage.BookPageClickListener {

    var currentPage: Int = 0
        private set

    private lateinit var presentList: List<Present>

    private val _leftPageInfo: MutableLiveData<PageInfo?> = MutableLiveData(null)
    val leftPageInfo: LiveData<PageInfo?> = _leftPageInfo

    private val _rightPageInfo: MutableLiveData<PageInfo?> = MutableLiveData(null)
    val rightPageInfo: LiveData<PageInfo?> = _rightPageInfo

    private val _pagingEvent: MutableLiveData<PagingEvent> = MutableLiveData()
    val pagingEvent: LiveData<PagingEvent> = _pagingEvent

    init {
        val fundingId = state.get<Int>(FUNDING_ID) ?: throw Exception("Funding id is invalid")
        loadPresentList(fundingId)
    }

    private fun loadPresentList(fundingId: Int) {
        viewModelScope.launch {
            when (val result = getPresentList(fundingId)) {
                is Result.Fail -> TODO()
                is Result.Loading -> TODO()
                is Result.Success -> {
                    presentList = result.data
                    fetchPageInfo(-2, 0)
                }
            }
        }
    }

    private fun getPageInfo(page: Int): PageInfo? {
        if (presentList.size / MAX_PRESENT_COUNT_EACH_PAGE < page) return null
        val startPosition = page * MAX_PRESENT_COUNT_EACH_PAGE
        val endPosition = Integer.min(
            startPosition + MAX_PRESENT_COUNT_EACH_PAGE,
            presentList.size
        )
        return PageInfo(
            page,
            presentList.subList(startPosition, endPosition),
            page - 1 in presentList.indices,
            page + 1 in presentList.indices
        )
    }

    private fun fetchPageInfo(from: Int, to: Int) {
        val isLeftPageChanged = from / 2 != to / 2
        if (isLeftPageChanged) {
            _leftPageInfo.postValue(getPageInfo(to / 2 * 2))
            _rightPageInfo.postValue(getPageInfo(to / 2 * 2 + 1))
        }
        _pagingEvent.postValue(PagingEvent(to % 2, isLeftPageChanged))
    }

    override fun onPrevClick() {
        val nextPage = currentPage - 1
        fetchPageInfo(currentPage, nextPage)
        currentPage = nextPage
    }

    override fun onNextClick() {
        val nextPage = currentPage + 1
        fetchPageInfo(currentPage, nextPage)
        currentPage = nextPage
    }

    override fun onPageClick(nextPage: Int) {
        fetchPageInfo(currentPage, nextPage)
        currentPage = nextPage
    }

    fun onPresentClicked(present: Present) {

    }

    data class PageInfo(
        val page: Int,
        val presentList: List<Present>,
        val isPrevAvailable: Boolean,
        val isNextAvailable: Boolean
    )

    data class PagingEvent(
        val toLeftPage: Int = 0,
        val needToDissolve: Boolean = false
    )
}
