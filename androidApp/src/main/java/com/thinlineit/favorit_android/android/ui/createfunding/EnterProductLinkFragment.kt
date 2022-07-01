package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterProductLinkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterProductLinkFragment :
    CreateFundingBaseFragment<FragmentEnterProductLinkBinding>(R.layout.fragment_enter_product_link) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@EnterProductLinkFragment.viewModel.also {
                it.currentFragment.value = CreateFundingViewModel.FragmentType.PRODUCT_LINK
                binding.progressButtons.setProgressState(it.progressStateList)
            }
            nextButton.setOnClickListener {
                if (viewModel?.productLinkState?.value == CreateFundingViewModel.InputState.AVAILABLE) {
                    navController.navigate(R.id.action_enterProductLinkFragment_to_enterProductOptionFragment)
                }
            }
        }
    }
}
