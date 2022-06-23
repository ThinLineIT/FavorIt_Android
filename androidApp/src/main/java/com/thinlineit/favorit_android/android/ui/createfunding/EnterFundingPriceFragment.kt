package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingPriceBinding

class EnterFundingPriceFragment :
    CreateFundingBaseFragment<FragmentEnterFundingPriceBinding>(R.layout.fragment_enter_funding_price) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            previousButton.setOnClickListener {
                navController.navigate(R.id.action_enterFundingPriceFragment_to_enterProductOptionFragment)
            }
            nextButton.setOnClickListener {
                if (viewModel?.productPriceState?.value == CreateFundingViewModel.InputState.AVAILABLE) {
                    navController.navigate(R.id.action_enterFundingPriceFragment_to_enterFundingNameFragment)
                }
            }
        }
    }
}
