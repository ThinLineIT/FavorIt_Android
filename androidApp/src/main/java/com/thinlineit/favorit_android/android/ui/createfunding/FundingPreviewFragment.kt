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
            viewModel = this@FundingPreviewFragment.viewModel
            previousButton.setOnClickListener {
                navController.navigate(R.id.action_fundingPreviewFragment_to_enterFundingExpiredDateFragment)
            }
            nextButton.setOnClickListener {
                navController.navigate(R.id.action_fundingPreviewFragment_to_finishCreateFundingFragment)
            }
        }
    }
}