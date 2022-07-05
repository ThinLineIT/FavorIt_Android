package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentFundingPreviewBinding

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
                navController.navigate(R.id.action_fundingPreviewFragment_to_finishCreateFundingFragment)
            }
        }
    }
}
