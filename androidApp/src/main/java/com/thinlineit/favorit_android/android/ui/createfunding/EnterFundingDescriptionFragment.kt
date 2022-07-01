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
            viewModel = this@EnterFundingDescriptionFragment.viewModel.also {
                it.currentFragment.value = CreateFundingViewModel.FragmentType.FUNDING_DESCRIPTION
                binding.progressButtons.setProgressState(it.progressStateList)
            }
            previousButton.setOnClickListener {
                navController.navigate(R.id.action_enterFundingDescriptionFragment_to_enterFundingNameFragment)
            }
            nextButton.setOnClickListener {
                if (viewModel?.fundingDescriptionState?.value == CreateFundingViewModel.InputState.AVAILABLE) {
                    navController.navigate(R.id.action_enterFundingDescriptionFragment_to_enterFundingExpiredDateFragment)
                }
            }
        }
    }
}
