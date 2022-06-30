package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingNameBinding

class EnterFundingNameFragment : CreateFundingBaseFragment<FragmentEnterFundingNameBinding>(R.layout.fragment_enter_funding_name){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@EnterFundingNameFragment.viewModel
            previousButton.setOnClickListener {
                navController.navigate(R.id.action_enterFundingNameFragment_to_enterFundingPriceFragment)
            }
            nextButton.setOnClickListener {
                if (viewModel?.fundingNameState?.value == CreateFundingViewModel.InputState.AVAILABLE) {
                    navController.navigate(R.id.action_enterFundingNameFragment_to_enterFundingDescriptionFragment)
                }
            }
        }
    }
}