package com.thinlineit.favorit_android.android.ui.settlefunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentFinishSettleFundingBinding
import com.thinlineit.favorit_android.android.ui.MainActivity
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity

class FinishSettleFundingFragment :
    SettleFundingBaseFragment<FragmentFinishSettleFundingBinding>(R.layout.fragment_finish_settle_funding) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@FinishSettleFundingFragment.viewModel
            initView()
        }
    }

    private fun initView() {
        binding.goToMain.setOnClickListener {
            MainActivity.start(requireActivity(), FundingDetailActivity.INVALID_FUNDING_ID)
        }
    }
}