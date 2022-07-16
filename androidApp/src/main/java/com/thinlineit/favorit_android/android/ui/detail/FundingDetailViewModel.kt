package com.thinlineit.favorit_android.android.ui.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.FundingState
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity.Companion.FUNDING_ID
import com.thinlineit.favorit_android.android.ui.detail.usecase.FundingDetailUseCase
import com.thinlineit.favorit_android.android.util.NumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FundingDetailViewModel @Inject constructor(
    private val fundingDetailUseCase: FundingDetailUseCase,
    state: SavedStateHandle
) : ViewModel() {
    val fundingId: Int = state.get<Int>(FUNDING_ID) ?: throw Exception("Funding id is invalid")
    val funding: MutableLiveData<Funding?> = MutableLiveData(null)

    val fundingPriceAsCurrency: LiveData<String> = Transformations.map(funding) {
        if (it == null) return@map ""
        else NumberFormatter.asCurrency(it.product.price.toLong())
    }

    val presentable: LiveData<Boolean> = Transformations.map(funding) { funding ->
        if (funding == null) return@map false
        funding.state == FundingState.OPENED
    }
    val goToPresentText: LiveData<Int> = Transformations.map(presentable) { presentable ->
        if (presentable) R.string.button_present
        else R.string.button_already_closed_funding
    }

    private val closable: LiveData<Boolean> = Transformations.map(funding) { funding ->
        if (funding == null) return@map false
        val isClosable =
            funding.state == FundingState.OPENED || funding.state == FundingState.EXPIRED
        funding.isMaker && isClosable
    }
    val askCloseFundingVisibility: LiveData<Int> = Transformations.map(closable) { closable ->
        if (closable) View.VISIBLE
        else View.GONE
    }

    private val _closeFundingResult = MutableLiveData<Result<Unit>>()
    val closeFundingResult: LiveData<Result<Unit>> = _closeFundingResult

    val showExpiredAlertDialog = Transformations.map(funding) { funding ->
        if (funding == null) return@map false
        funding.state == FundingState.EXPIRED && funding.isMaker
    }

    val goToClosedFundingActivity = Transformations.map(funding) { funding ->
        if (funding == null) return@map false
        funding.state == FundingState.CLOSED && funding.isMaker
    }

    init {
        loadFundingDetail(fundingId)
    }

    private fun loadFundingDetail(fundingId: Int) {
        viewModelScope.launch {
            funding.postValue(fundingDetailUseCase.getFunding(fundingId))
        }
    }

    fun closeFunding() {
        viewModelScope.launch {
            _closeFundingResult.postValue(fundingDetailUseCase.closeFunding(fundingId))
        }
    }
}
