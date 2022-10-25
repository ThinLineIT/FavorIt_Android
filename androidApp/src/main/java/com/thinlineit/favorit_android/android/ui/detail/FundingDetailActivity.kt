package com.thinlineit.favorit_android.android.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.databinding.ActivityFundingDetailBinding
import com.thinlineit.favorit_android.android.ui.present.PresentActivity
import com.thinlineit.favorit_android.android.util.longToast
import com.thinlineit.favorit_android.android.util.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FundingDetailActivity : AppCompatActivity() {
    private val binding: ActivityFundingDetailBinding by lazy {
        ActivityFundingDetailBinding.inflate(layoutInflater)
    }
    val viewModel: FundingDetailViewModel by lazy {
        ViewModelProvider(this)[FundingDetailViewModel::class.java].apply {
            val fundingId = intent.getIntExtra(FUNDING_ID, INVALID_FUNDING_ID)
            loadFundingDetail(fundingId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserver()
        initView()
    }

    private fun initObserver() {
        viewModel.loadFundingResult.observe(this) {
            when (it) {
                is Result.Loading -> Unit
                is Result.Fail -> {
                    shortToast("Fail to load funding")
                    finish()
                }
                is Result.Success -> Unit
            }
        }
        viewModel.intentLiveData.observe(this) {
            startActivity(intent)
        }
    }

    private fun initView() {
        binding.presentButton.setOnClickListener {
            viewModel.present()
            if (viewModel.presentable.value == true) {
                PresentActivity.start(this, viewModel.fundingId)
            }
        }
        binding.fundingInfoButton.setOnClickListener {
            // TODO: show funding info dialog
        }
        binding.copyFundingLinkButton.setOnClickListener {
            val fundingLink = viewModel.funding.value?.fundingLink
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(getString(R.string.label_funding_link), fundingLink)
            clipboardManager.setPrimaryClip(clip)
            longToast(getString(R.string.label_copy_complete))
        }
        binding.settleButton.setOnClickListener {
            viewModel.settle()
        }
        binding.closeButton.setOnClickListener {
            viewModel.close()
        }
    }

    companion object {
        fun start(context: Context, fundingId: Int) {
            val intent = Intent(context, FundingDetailActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
            }
            context.startActivity(intent)
        }

        const val FUNDING_ID = "FUNDING_ID"
        const val INVALID_FUNDING_ID = -1
    }
}
