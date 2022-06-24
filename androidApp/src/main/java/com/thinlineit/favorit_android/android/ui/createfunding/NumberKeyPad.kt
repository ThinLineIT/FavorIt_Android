package com.thinlineit.favorit_android.android.ui.createfunding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.thinlineit.favorit_android.android.databinding.NumberKeyPadBinding
import com.thinlineit.favorit_android.android.util.longToast

class NumberKeyPad @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var numberResult: MutableLiveData<Long>? = null
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

    private fun onNumberClick(enteredNumber: String) {
        val currentNumber = numberResult?.value ?: return
        try {
            val newNumber = (currentNumber.toString() + enteredNumber).toLong()
            updateNumberResult(newNumber)
        } catch (exception: NumberFormatException) {
            context.longToast(
                """
                    currentNumber: $currentNumber 
                    enteredNumber: $enteredNumber newNumber: 
                    ${currentNumber.toString() + enteredNumber}
                """.trimMargin()
            )
        }
    }

    private fun onDeleteClick() {
        val currentNumber = numberResult?.value ?: return
        val newNumber = currentNumber / 10
        updateNumberResult(newNumber)
    }

    private fun updateNumberResult(newNumber: Long) {
        numberResult?.postValue(newNumber)
    }
}
