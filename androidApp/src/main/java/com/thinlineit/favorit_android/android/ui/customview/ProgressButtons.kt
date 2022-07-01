package com.thinlineit.favorit_android.android.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.ProgressButtonsBinding

class ProgressButtons @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val binding: ProgressButtonsBinding =
        ProgressButtonsBinding.inflate(LayoutInflater.from(context), this, true)
    private val lifecycleOwner by lazy {
        findViewTreeLifecycleOwner() ?: throw Exception("Lifecycle is not exist")
    }
    var circles: List<ImageView> = listOf(
        binding.productLinkProgressCircle,
        binding.productOptionProgress,
        binding.fundingPriceProgress,
        binding.fundingNameProgress,
        binding.fundingDescriptionProgress,
        binding.fundingExpiredDateProgress
    )

    fun setProgressState(
        progressStates: List<MediatorLiveData<ProgressState>>
    ) {
        for (state in 0..5) {
            progressStates[state].observe(lifecycleOwner) {
                val image = it.asImage()
                circles[state].setImageResource(image)
            }
        }
    }

    private fun ProgressState.asImage() =
        when (this) {
            ProgressState.EMPTY ->
                R.drawable.icon_circle_progress_button_empty
            ProgressState.EDITING ->
                R.drawable.icon_circle_progress_button_editing
            ProgressState.CORRECT_ENTERED ->
                R.drawable.icon_circle_progress_button_correct_entered
            ProgressState.COMPLETE ->
                R.drawable.icon_circle_progress_button_complete
        }

    enum class ProgressState {
        EMPTY, EDITING, CORRECT_ENTERED, COMPLETE
    }
}


