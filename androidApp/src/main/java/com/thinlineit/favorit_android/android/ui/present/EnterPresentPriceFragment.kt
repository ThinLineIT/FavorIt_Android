package com.thinlineit.favorit_android.android.ui.present

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterPresentPriceBinding
import com.thinlineit.favorit_android.android.ui.customview.NumberKeyPadView

class EnterPresentPriceFragment :
    PresentBaseFragment<FragmentEnterPresentPriceBinding>(R.layout.fragment_enter_present_price) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initButtonListener()
        initNumberKeyPadView(binding.numberKeyPad)
        initPriceCursorAnimation(binding.priceCursor)
    }

    private fun initButtonListener() {
        binding.apply {
            previousButton.setOnClickListener {
                requireActivity().finish()
            }
            nextButton.setOnClickListener {
                navController.navigate(
                    EnterPresentPriceFragmentDirections
                        .actionEnterPresentPriceFragmentToEnterPresentConfirmFragment()
                )
            }
        }
    }

    private fun initNumberKeyPadView(numberKeyPad: NumberKeyPadView) {
        viewModel.presentPrice.value?.let { initialPrice ->
            binding.numberKeyPad.init(initialPrice)
        }
        numberKeyPad.numberResult.observe(viewLifecycleOwner) { newPrice ->
            viewModel.presentPrice.postValue(newPrice)
        }
    }

    private fun initPriceCursorAnimation(priceCursor: View) {
        val cursorAnim = AnimationUtils.loadAnimation(context, R.anim.blink_animation)
        this.viewModel.presentPriceState.observe(viewLifecycleOwner) {
            if (it === PresentViewModel.InputState.EMPTY) {
                priceCursor.startAnimation(cursorAnim)
            } else {
                priceCursor.clearAnimation()
            }
        }
    }
}
