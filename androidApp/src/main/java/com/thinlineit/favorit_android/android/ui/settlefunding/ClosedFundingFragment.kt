package com.thinlineit.favorit_android.android.ui.settlefunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentClosedFundingBinding

class ClosedFundingFragment : SettleFundingBaseFragment<FragmentClosedFundingBinding>(
    R.layout.fragment_closed_funding
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@ClosedFundingFragment.viewModel
            goToSettleFunding.setOnClickListener {
                navController.navigate(
                    ClosedFundingFragmentDirections
                        .actionClosedFundingFragmentToSelectBankFragment()
                )
            }
        }
    }
}
