package com.thinlineit.favorit_android.android.ui.detail

import android.content.Context
import android.content.Intent
import android.text.Html
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.FundingState
import com.thinlineit.favorit_android.android.data.entity.Present
import com.thinlineit.favorit_android.android.ui.detail.usecase.FundingDetailUseCase
import com.thinlineit.favorit_android.android.ui.present.PresentActivity
import com.thinlineit.favorit_android.android.util.dDayFromToday
import com.thinlineit.favorit_android.android.util.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FundingDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fundingDetailUseCase: FundingDetailUseCase,
) : ViewModel() {
    var fundingId: Int = 0

    private val _loadFundingResult = MutableLiveData<Result<Funding>>(Result.Loading(false))
    val loadFundingResult: LiveData<Result<Funding>> = _loadFundingResult

    val intentLiveData = MutableLiveData<Intent>()

    val funding: MutableLiveData<Funding> = MutableLiveData()
    val fundingExpiredDateString: LiveData<String> = Transformations.map(funding) {
        when (it.state) {
            FundingState.OPENED -> {
                val dDay = it.expiredDate.toDate()?.dDayFromToday()
                context.resources.getString(R.string.funding_detail_progress_expired_date, dDay)
            }
            FundingState.EXPIRED -> {
                "펀딩이 만료되었습니다."
            }
            FundingState.CLOSED -> {
                "펀딩이 마감되었습니다."
            }
            FundingState.COMPLETED -> {
                "펀딩이 완료되었습니다."
            }
        }
    }
    val presentList: MutableLiveData<List<Present>> = MutableLiveData()
    val presentStatusString: LiveData<String> = Transformations.map(presentList) {
        context.resources.getString(R.string.funding_detail_progress_present_status, it.count())
    }

    val fundingStatus: LiveData<FundingState> = Transformations.map(funding) {
        it.state
    }

    val isClosable: Boolean
        get() {
            val funding = funding.value ?: return false
            val isNotClosed =
                funding.state == FundingState.OPENED || funding.state == FundingState.EXPIRED
            return funding.isMaker && isNotClosed
        }


    fun loadFundingDetail(fundingId: Int) {
        this.fundingId = fundingId

        viewModelScope.launch {
            _loadFundingResult.postValue(Result.Loading(true))
            val loadFundingResult = fundingDetailUseCase.getFunding(fundingId)
            _loadFundingResult.postValue(loadFundingResult)
            if (loadFundingResult is Result.Success) {
                funding.value = loadFundingResult.data
            }
        }
        viewModelScope.launch {
            val result = fundingDetailUseCase.getPresentList(fundingId)
            if (result is Result.Success) {
                presentList.value = result.data
            }
        }
    }

    fun present(context: Context) {
        val presentable = funding.value?.state == FundingState.OPENED
        if (presentable) {
            intentLiveData.value = PresentActivity.getIntent(context, fundingId)
        }
    }

    fun close() {
        viewModelScope.launch {
            if (isClosable) {
                val result = fundingDetailUseCase.closeFunding(fundingId)
                if (result is Result.Success) {
                    loadFundingDetail(fundingId)
                }
            }
        }
    }

    fun settle() {
        viewModelScope.launch {
            val funding = funding.value ?: return@launch
            if (funding.state == FundingState.CLOSED) {
                // TODO: Go to celebrate funding activity
            }
        }
    }
}
