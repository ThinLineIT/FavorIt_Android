package com.thinlineit.favorit_android.android.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.thinlineit.favorit_android.android.databinding.IndicatorProgressBarBinding

class IndicatorProgressBar(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    val binding: IndicatorProgressBarBinding =
        IndicatorProgressBarBinding.inflate(LayoutInflater.from(context), this, true)

    fun setProgress(percentage: Int) {
        binding.progressBar.progress = percentage
        binding.percentTextView.text = "$percentage%"
        val layoutParams = (binding.percentTextView.layoutParams as LayoutParams).apply {
            horizontalBias = percentage / 100f
        }
        binding.percentTextView.layoutParams = layoutParams
    }
}