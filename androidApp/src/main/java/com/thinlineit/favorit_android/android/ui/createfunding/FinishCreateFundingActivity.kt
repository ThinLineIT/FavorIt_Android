package com.thinlineit.favorit_android.android.ui.createfunding

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.ActivityFinishCreateFundingBinding
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.util.longToast

class FinishCreateFundingActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFinishCreateFundingBinding.inflate(LayoutInflater.from(this))
    }
    private val args: FinishCreateFundingActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val fundingLink = args.fundingLink
        val fundingID = args.fundingID
        binding.apply {
            copyLinkButton.setOnClickListener {
                val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip =
                    ClipData.newPlainText(getString(R.string.label_funding_link), fundingLink)
                clipboardManager.setPrimaryClip(clip)
                longToast(getString(R.string.label_copy_complete))
            }

            goToFundingDetailButton.setOnClickListener {
                FundingDetailActivity.start(this@FinishCreateFundingActivity, fundingID)
            }
        }
    }
}
