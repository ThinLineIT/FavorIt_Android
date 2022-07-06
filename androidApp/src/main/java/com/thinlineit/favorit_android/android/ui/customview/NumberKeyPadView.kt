package com.thinlineit.favorit_android.android.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thinlineit.favorit_android.android.databinding.NumberKeyPadBinding
import com.thinlineit.favorit_android.android.util.longToast

class NumberKeyPadView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val _numberResult = MutableLiveData(0)
    val numberResult: LiveData<Int> = _numberResult
    val binding: NumberKeyPadBinding

    init {
        this.binding = NumberKeyPadBinding.inflate(LayoutInflater.from(context), this, true)
        binding.numberKeyPadOne.setOnClickListener {
            onNumberClick("1")
        }
        binding.numberKeyPadTwo.setOnClickListener {
            onNumberClick("2")
        }
        binding.numberKeyPadThree.setOnClickListener {
            onNumberClick("3")
        }
        binding.numberKeyPadFour.setOnClickListener {
            onNumberClick("4")
        }
        binding.numberKeyPadFive.setOnClickListener {
            onNumberClick("5")
        }
        binding.numberKeyPadSix.setOnClickListener {
            onNumberClick("6")
        }
        binding.numberKeyPadSeven.setOnClickListener {
            onNumberClick("7")
        }
        binding.numberKeyPadEight.setOnClickListener {
            onNumberClick("8")
        }
        binding.numberKeyPadNine.setOnClickListener {
            onNumberClick("9")
        }
        binding.numberKeyPadDoubleZero.setOnClickListener {
            onNumberClick("00")
        }
        binding.numberKeyPadZero.setOnClickListener {
            onNumberClick("0")
        }
        binding.numberKeyPadDelete.setOnClickListener {
            onDeleteClick()
        }
    }

    fun init(initialNumber: Int) {
        updateNumberResult(initialNumber)
    }

    private fun onNumberClick(enteredNumber: String) {
        val currentNumber = numberResult.value ?: return
        try {
            val newNumber = (currentNumber.toString() + enteredNumber).toInt()
            updateNumberResult(newNumber)
        } catch (exception: NumberFormatException) {
            context.longToast("잘못된 숫자입니다. 어떻게 입력한거에요?")
        }
    }

    private fun onDeleteClick() {
        val currentNumber = _numberResult.value ?: return
        val newNumber =
            currentNumber.toString().dropLast(1).takeIf { it.isNotEmpty() }?.toInt() ?: 0
        updateNumberResult(newNumber)
    }

    private fun updateNumberResult(newNumber: Int) {
        if (newNumber > MAX_NUMBER) {
            context.longToast("최대 10억까지만 설정이 가능해요 ㅠ")
        }
        _numberResult.postValue(newNumber)
    }

    companion object {
        private const val MAX_NUMBER = 1000000000
    }
}
