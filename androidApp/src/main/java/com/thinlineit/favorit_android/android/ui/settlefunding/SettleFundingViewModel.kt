package com.thinlineit.favorit_android.android.ui.settlefunding

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Account
import com.thinlineit.favorit_android.android.data.entity.Bank
import com.thinlineit.favorit_android.android.data.entity.SettleFundingRequest
import com.thinlineit.favorit_android.android.data.repository.FundingRepository
import com.thinlineit.favorit_android.android.ui.settlefunding.SettleFundingActivity.Companion.FUNDING_ID
import com.thinlineit.favorit_android.android.ui.settlefunding.SettleFundingActivity.Companion.FUNDING_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettleFundingViewModel @Inject constructor(
    private val fundingRepository: FundingRepository,
    state: SavedStateHandle
) : ViewModel() {
    val fundingId: Int =
        state.get<Int>(FUNDING_ID) ?: throw Exception("Funding id is invalid")
    val fundingName: String =
        state.get<String>(FUNDING_NAME) ?: throw Exception("Funding name is invalid")
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
        when (it) {
            "" -> InputState.EMPTY
            else -> InputState.AVAILABLE
        }
    }

    val settleFundingResult = MutableLiveData<Result<Unit>>()

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

    fun settleFunding() {
        viewModelScope.launch {
            settleFundingResult.postValue(Result.Loading(true))
            val settleFundingRequest = getSettleFunding() ?: run {
                settleFundingResult.postValue(Result.Fail(Exception("Some value is null")))
                return@launch
            }
            val result = fundingRepository.settleFunding(settleFundingRequest)
            settleFundingResult.postValue(result)
        }
    }

    private fun getSettleFunding(): SettleFundingRequest? {
        val bankCode = selectedBankValue.value ?: return null
        val fullName = accountOwnerName.value ?: return null
        val bankAccount = bankAccount.value ?: return null
        return SettleFundingRequest(
            fundingId,
            bankCode,
            fullName,
            bankAccount
        )
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
