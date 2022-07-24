package com.thinlineit.favorit_android.android.ui.createfunding

import androidx.lifecycle.MutableLiveData
import com.thinlineit.favorit_android.android.ui.customview.numberkeypad.NumberKeyPadView

class FundingPriceOnNumberClickListener(
    private val fundingPrice: MutableLiveData<Int>,
    private val toastLiveData: MutableLiveData<String>
) : NumberKeyPadView.OnNumberClickListener {
    override fun onNumberClick(clickedNumber: String) {
        val currentPrice = fundingPrice.value ?: return
        try {
            val newPrice = (currentPrice.toString() + clickedNumber).toInt()
            updatePrice(newPrice)
        } catch (exception: NumberFormatException) {
            toastLiveData.postValue("잘못된 숫자입니다. 어떻게 입력한거에요?")
        }
    }

    override fun onDeleteClick() {
        val currentPrice = fundingPrice.value ?: return
        val newPrice =
            currentPrice.toString().dropLast(1).takeIf { it.isNotEmpty() }?.toInt() ?: 0
        updatePrice(newPrice)
    }

    private fun updatePrice(price: Int) {
        if (price > MAX_NUMBER) {
            toastLiveData.postValue("최대 10억까지만 설정이 가능해요 ㅠ")
        }
        fundingPrice.postValue(price)
    }

    companion object {
        private const val MAX_NUMBER = 1000000000
    }
}
