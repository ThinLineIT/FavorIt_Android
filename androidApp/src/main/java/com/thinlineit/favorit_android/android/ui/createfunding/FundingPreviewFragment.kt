package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.databinding.FragmentFundingPreviewBinding
import com.thinlineit.favorit_android.android.util.shortToast

class FundingPreviewFragment :
    CreateFundingBaseFragment<FragmentFundingPreviewBinding>(R.layout.fragment_funding_preview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@FundingPreviewFragment.viewModel.also {
                priceProgress.progress = 0
            }
            previousButton.setOnClickListener {
                navController.navigate(
                    FundingPreviewFragmentDirections
                        .actionFundingPreviewFragmentToEnterFundingExpiredDateFragment()
                )
            }
            nextButton.setOnClickListener {
                this@FundingPreviewFragment.viewModel.createFunding()
            }
        }
        initObserver()
    }

    private fun initObserver() {
        viewModel.createFundingResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.nextButton.isEnabled = !result.isLoading
                }
                is Result.Fail -> {
                    binding.nextButton.isEnabled = true
                    requireContext().shortToast("Something is wrong. Please try again.")
                }
                is Result.Success -> {
                    binding.nextButton.isEnabled = true
                    navController.navigate(
                        FundingPreviewFragmentDirections
                            .actionFundingPreviewFragmentToFinishCreateFundingActivity(
                                result.data.fundingLink, result.data.fundingID
                            )
                    )
                }
            }
        }
    }
}
