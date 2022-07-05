package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingPriceBinding
import com.thinlineit.favorit_android.android.util.shortToast

class EnterFundingPriceFragment :
    CreateFundingBaseFragment<FragmentEnterFundingPriceBinding>(R.layout.fragment_enter_funding_price) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cursorAnim = AnimationUtils.loadAnimation(context, R.anim.blink_animation)

        binding.apply {
            viewModel = this@EnterFundingPriceFragment.viewModel.apply {
                currentFragment.value = CreateFundingViewModel.FragmentType.FUNDING_PRICE
            }.also {
                numberKeyPad.numberResult = it.fundingPrice
                progressButtons.setProgressState(it.progressStateList)
            }
            previousButton.setOnClickListener {
                navController.navigate(
                    EnterFundingPriceFragmentDirections
                        .actionEnterFundingPriceFragmentToEnterProductOptionFragment()
                )
            }
            nextButton.setOnClickListener {
                navController.navigate(
                    EnterFundingPriceFragmentDirections
                        .actionEnterFundingPriceFragmentToEnterFundingNameFragment()
                )
            }
            this@EnterFundingPriceFragment.viewModel.fundingPriceState.observe(viewLifecycleOwner) {
                if (it === CreateFundingViewModel.InputState.EMPTY) {
                    priceCursor.startAnimation(cursorAnim)
                } else {
                    priceCursor.clearAnimation()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
