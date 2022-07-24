package com.thinlineit.favorit_android.android.ui.customview.numberkeypad

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.thinlineit.favorit_android.android.databinding.NumberKeyPadBinding

class NumberKeyPadView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding: NumberKeyPadBinding
    lateinit var onNumberClickListener: OnNumberClickListener

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

    fun init(onNumberClickListener: OnNumberClickListener) {
        this.onNumberClickListener = onNumberClickListener
    }

    private fun onNumberClick(clickedNumber: String) {
        onNumberClickListener.onNumberClick(clickedNumber)
    }

    private fun onDeleteClick() {
        onNumberClickListener.onDeleteClick()
    }

    interface OnNumberClickListener {
        fun onNumberClick(clickedNumber: String)
        fun onDeleteClick()
    }
}
