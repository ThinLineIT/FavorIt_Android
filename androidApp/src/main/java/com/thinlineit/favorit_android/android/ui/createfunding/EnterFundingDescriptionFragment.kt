package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingDescriptionBinding

class EnterFundingDescriptionFragment :
    CreateFundingBaseFragment<FragmentEnterFundingDescriptionBinding>(R.layout.fragment_enter_funding_description) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@EnterFundingDescriptionFragment.viewModel.apply {
                currentFragment.value = CreateFundingViewModel.FragmentType.FUNDING_DESCRIPTION
            }.also {
                progressButtons.setProgressState(it.progressStateList)
            }
            previousButton.setOnClickListener {
                navController.navigate(
                    EnterFundingDescriptionFragmentDirections
                        .actionEnterFundingDescriptionFragmentToEnterFundingNameFragment()
                )
            }
            nextButton.setOnClickListener {
                navController.navigate(
                    EnterFundingDescriptionFragmentDirections
                        .actionEnterFundingDescriptionFragmentToEnterFundingExpiredDateFragment()
                )
            }
        }
    }
}
