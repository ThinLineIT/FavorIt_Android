package com.thinlineit.favorit_android.android.ui.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.FundingState
import com.thinlineit.favorit_android.android.ui.detail.usecase.FundingDetailUseCase
import com.thinlineit.favorit_android.android.util.NumberFormatter
import com.thinlineit.favorit_android.android.util.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FundingDetailViewModel @Inject constructor(
    private val fundingDetailUseCase: FundingDetailUseCase,
) : ViewModel() {
    var fundingId: Int = 0

    private val _loadFundingResult = MutableLiveData<Result<Funding>>(Result.Loading(false))
    val loadFundingResult: LiveData<Result<Funding>> = _loadFundingResult

    val funding: LiveData<Funding?> = Transformations.map(loadFundingResult) {
        when (it) {
            is Result.Loading -> null
            is Result.Fail -> null
            is Result.Success -> it.data
        }
    }

    val fundingDateProgressPercentage: LiveData<Int> = Transformations.map(funding) { funding ->
        if (funding == null) return@map 0
        val startDateTime = funding.startDate.toDate()?.time ?: return@map 0
        val endDateTime = funding.expiredDate.toDate()?.time ?: return@map 0
        val currentTime = System.currentTimeMillis()
        return@map ((currentTime - startDateTime) / (endDateTime - startDateTime).toFloat() * 100).toInt()
            .takeIf { it <= 100 } ?: 100
    }

    val fundingPriceAsCurrency: LiveData<String> = Transformations.map(funding) { funding ->
        if (funding == null) return@map ""
        else NumberFormatter.asCurrency(funding.product.price.toLong())
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

    fun loadFundingDetail(fundingId: Int) {
        this.fundingId = fundingId

        viewModelScope.launch {
            _loadFundingResult.postValue(Result.Loading(true))
            _loadFundingResult.postValue(fundingDetailUseCase.getFunding(fundingId))
        }
    }

    fun closeFunding() {
        viewModelScope.launch {
            _closeFundingResult.postValue(fundingDetailUseCase.closeFunding(fundingId))
        }
    }
}
