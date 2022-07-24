package com.thinlineit.favorit_android.android.ui.settlefunding

import androidx.lifecycle.MutableLiveData
import com.thinlineit.favorit_android.android.ui.customview.numberkeypad.NumberKeyPadView

class BankAccountOnNumberClickListener(
    private val bankAccount: MutableLiveData<String>,
    private val toastLiveData: MutableLiveData<String>
) : NumberKeyPadView.OnNumberClickListener {
    override fun onNumberClick(clickedNumber: String) {
        val currentInput = bankAccount.value ?: return
        try {
            val newInput = (currentInput + clickedNumber)
            updateInput(newInput)
        } catch (e: NumberFormatException) {
            toastLiveData.postValue("잘못된 숫자입니다. 어떻게 입력한거에요?")
        }
    }

    override fun onDeleteClick() {
        val currentInput = bankAccount.value ?: return
        val newInput = currentInput.dropLast(1).takeIf { it.isNotEmpty() } ?: ""
        updateInput(newInput)
    }

    private fun updateInput(input: String) {
        bankAccount.postValue(input)
    }
}
