package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterProductOptionBinding

class EnterProductOptionFragment :
    CreateFundingBaseFragment<FragmentEnterProductOptionBinding>(R.layout.fragment_enter_product_option) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            previousButton.setOnClickListener {
                navController.navigate(R.id.action_enterProductOptionFragment_to_enterProductLinkFragment)
            }
            nextButton.setOnClickListener {
                if (viewModel?.productOptionState?.value == CreateFundingViewModel.InputState.AVAILABLE) {
                    navController.navigate(R.id.action_enterProductOptionFragment_to_enterFundingPriceFragment)
                }
            }
        }
    }
}
