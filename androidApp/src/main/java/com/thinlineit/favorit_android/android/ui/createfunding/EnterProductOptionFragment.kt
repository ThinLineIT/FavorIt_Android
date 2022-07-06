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
            viewModel = this@EnterProductOptionFragment.viewModel.apply {
                currentFragment.value = CreateFundingViewModel.FragmentType.PRODUCT_OPTION
            }.also {
                progressButtons.setProgressState(it.progressStateList)
            }
            previousButton.setOnClickListener {
                navController.navigate(
                    EnterProductOptionFragmentDirections
                        .actionEnterProductOptionFragmentToEnterProductLinkFragment()
                )
            }
            nextButton.setOnClickListener {
                navController.navigate(
                    EnterProductOptionFragmentDirections
                        .actionEnterProductOptionFragmentToEnterFundingPriceFragment()
                )
            }
        }
    }
}
