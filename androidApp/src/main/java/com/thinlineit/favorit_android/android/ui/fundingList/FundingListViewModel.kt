package com.thinlineit.favorit_android.android.ui.fundingList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.FundingInfo
import com.thinlineit.favorit_android.android.data.entity.FundingLists
import com.thinlineit.favorit_android.android.ui.fundingList.usecase.FundingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FundingListViewModel @Inject constructor(
    private val fundingListUseCase: FundingListUseCase
) : ViewModel() {
    private val _loadFundingListsResult =
        MutableLiveData<Result<FundingLists>>(Result.Loading(false))
    val loadFundingListsResult: LiveData<Result<FundingLists>> = _loadFundingListsResult

    val myFundingList: LiveData<List<FundingInfo>> = Transformations.map(loadFundingListsResult) {
        when (it) {
            is Result.Loading -> null
            is Result.Fail -> null
            is Result.Success -> it.data.myFundings
        }
    }

    val friendFundingList: LiveData<List<FundingInfo>> =
        Transformations.map(loadFundingListsResult) {
            when (it) {
                is Result.Loading -> null
                is Result.Fail -> null
                is Result.Success -> it.data.friendsFundings
            }
        }

    fun getFundingList() {
        viewModelScope.launch {
            _loadFundingListsResult.postValue(Result.Loading(true))
            _loadFundingListsResult.postValue(fundingListUseCase.getFundingList())
        }
    }
}
