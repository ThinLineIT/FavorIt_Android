package com.thinlineit.favorit_android.android.ui.present

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.databinding.FragmentFinishPresentBinding
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.util.longToast

class FinishPresentFragment :
    PresentBaseFragment<FragmentFinishPresentBinding>(R.layout.fragment_finish_present) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initButtonListener()
    }

    private fun initButtonListener() {
        binding.copyLinkButton.setOnClickListener {
            val result =
                if (viewModel.presentResult.value is Result.Success)
                    viewModel.presentResult.value as Result.Success
                else
                    return@setOnClickListener
            val fundingLink = result.data.fundingLink
            val clipboardManager =
                requireActivity().getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip =
                ClipData.newPlainText(getString(R.string.label_funding_link), fundingLink)
            clipboardManager.setPrimaryClip(clip)
            requireActivity().longToast(getString(R.string.label_copy_complete))
        }

        binding.goToFundingDetailButton.setOnClickListener {
            val fundingId = viewModel.fundingId
            FundingDetailActivity.start(requireActivity(), fundingId)
        }
    }
}