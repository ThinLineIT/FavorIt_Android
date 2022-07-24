package com.thinlineit.favorit_android.android.ui.settlefunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentConfirmBankAccountBinding


class ConfirmBankAccountFragment :
    SettleFundingBaseFragment<FragmentConfirmBankAccountBinding>(
        R.layout.fragment_confirm_bank_account
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@ConfirmBankAccountFragment.viewModel
            initView()
        }
    }

    private fun initView() {
        binding.previousButton.setOnClickListener {
            navController.navigate(
                ConfirmBankAccountFragmentDirections
                    .actionConfirmBankAccountFragmentToSelectBankFragment()
            )
        }
        binding.nextButton.setOnClickListener {
            navController.navigate(
                ConfirmBankAccountFragmentDirections
                    .actionConfirmBankAccountFragmentToFinishSettleFundingFragment()
            )
        }
    }
}