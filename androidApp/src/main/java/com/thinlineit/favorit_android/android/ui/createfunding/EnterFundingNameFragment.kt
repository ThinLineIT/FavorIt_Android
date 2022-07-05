package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingNameBinding

class EnterFundingNameFragment :
    CreateFundingBaseFragment<FragmentEnterFundingNameBinding>(R.layout.fragment_enter_funding_name) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@EnterFundingNameFragment.viewModel.apply {
                currentFragment.value = CreateFundingViewModel.FragmentType.FUNDING_NAME
            }.also {
                progressButtons.setProgressState(it.progressStateList)
            }
            previousButton.setOnClickListener {
                navController.navigate(
                    EnterFundingNameFragmentDirections
                        .actionEnterFundingNameFragmentToEnterFundingPriceFragment()
                )
            }
            nextButton.setOnClickListener {
                navController.navigate(
                    EnterFundingNameFragmentDirections
                        .actionEnterFundingNameFragmentToEnterFundingDescriptionFragment()
                )
            }
        }
    }
}
