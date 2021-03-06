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
            viewModel = this@EnterProductLinkFragment.viewModel.apply {
                currentFragment.value = CreateFundingViewModel.FragmentType.PRODUCT_LINK
            }.also {
                progressButtons.setProgressState(it.progressStateList)
            }
            nextButton.setOnClickListener {
                navController.navigate(
                    EnterProductLinkFragmentDirections
                        .actionEnterProductLinkFragmentToEnterProductOptionFragment()
                )
            }
        }
    }
}
