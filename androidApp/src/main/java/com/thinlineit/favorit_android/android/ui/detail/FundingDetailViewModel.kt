package com.thinlineit.favorit_android.android.ui.detail

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val intentLiveData = MutableLiveData<Intent>()

    var funding: Funding? = null

    fun loadFundingDetail(fundingId: Int) {
        this.fundingId = fundingId

        viewModelScope.launch {
            _loadFundingResult.postValue(Result.Loading(true))
            _loadFundingResult.postValue(fundingDetailUseCase.getFunding(fundingId))
           val loadFundingResult = fundingDetailUseCase.getFunding(fundingId)
           _loadFundingResult.postValue(loadFundingResult)
            if (loadFundingResult is Result.Success) {
                funding = loadFundingResult.data
            }
        }
    }

    fun present() {
        TODO("Not yet implemented")
    }

    fun close() {
        viewModelScope.launch {
            fundingDetailUseCase.closeFunding(fundingId)
        }
    }

    fun settle() {
        TODO("Not yet implemented")
    }
}
