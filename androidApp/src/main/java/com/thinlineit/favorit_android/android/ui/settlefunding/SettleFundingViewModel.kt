package com.thinlineit.favorit_android.android.ui.settlefunding

import android.view.View
import androidx.lifecycle.*
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Account
import com.thinlineit.favorit_android.android.data.entity.Bank
import com.thinlineit.favorit_android.android.data.repository.FundingRepository
import com.thinlineit.favorit_android.android.ui.settlefunding.SettleFundingActivity.Companion.FUNDING_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettleFundingViewModel @Inject constructor(
    private val fundingRepository: FundingRepository,
    state: SavedStateHandle
) : ViewModel() {
    val fundingName: String =
        state.get<String>(FUNDING_NAME) ?: throw Exception("Funding id is invalid")
    val selectedBankValue = MutableLiveData("")
    val selectedBankText = MutableLiveData("")
    val bankAccount = MutableLiveData("")
    val accountOwnerName = MutableLiveData("")
    val accountOwnerNameResult = MutableLiveData<Result<Account>>()

    val toastMessage: MutableLiveData<String> = MutableLiveData("")

    val bankAccountOnNumberClickListener = BankAccountOnNumberClickListener(
        bankAccount,
        toastMessage
    )

    private val _bankList = MutableLiveData<List<Bank>>()
    val bankList: LiveData<List<Bank>>
        get() = _bankList

    val fundingAccountState: LiveData<InputState> = Transformations.map(bankAccount) {
        when {
            it == "" -> InputState.EMPTY
            else -> InputState.AVAILABLE
        }
    }

    fun loadBankList() {
        viewModelScope.launch {
            _bankList.value = fundingRepository.getBankList()
        }
    }

    fun selectBank(value: String, text: String) {
        selectedBankValue.value = value
        selectedBankText.value = text
    }

    fun checkBankAccount() {
        viewModelScope.launch {
            val result = fundingRepository.checkBankAccount(
                selectedBankValue.value.toString(),
                bankAccount.value.toString()
            )
            accountOwnerNameResult.postValue(result)
            if (result is Result.Success) {
                accountOwnerName.postValue(result.data.accountOwnerName)
            }
        }
    }


    sealed class InputState {
        object EMPTY : InputState()
        object AVAILABLE : InputState()
        object UNAVAILABLE : InputState()

        fun toCursorVisibility() = if (this == EMPTY) {
            View.VISIBLE
        } else {
            View.GONE
        }

    }
}
