package com.thinlineit.favorit_android.android.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.FundingState
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
        ViewModelProvider(this)[FundingDetailViewModel::class.java]
    }
    val fundingId: Int by lazy {
        intent.getIntExtra(FUNDING_ID, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            lifecycleOwner = this@FundingDetailActivity
            viewModel = this@FundingDetailActivity.viewModel
        }
        viewModel.loadFundingDetail(fundingId)
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.closeFundingResult.observe(this) {
            when (it) {
                is Result.Loading -> {
                    // nothing to do
                }
                is Result.Fail -> {
                    shortToast("Fail to close funding")
                }
                is Result.Success -> {
                    viewModel.loadFundingDetail(fundingId)
                    binding.detailActionLayout.visibility = View.VISIBLE
                    binding.detailActionLayoutWhenAskingClose.visibility = View.GONE
                }
            }
        }
        viewModel.fundingState.observe(this) {
            if (it == FundingState.EXPIRED) {
                ExpiredAlertDialog().show(supportFragmentManager, "ExpiredAlert")
            }
        }
    }

    private fun initView() {
        binding.goToSeeProductTextView.setOnClickListener {
            val link = viewModel.funding.value?.product?.link
                ?: run {
                    shortToast("ProductLink is invalid")
                    return@setOnClickListener
                }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
        binding.fundingLinkButtonLayout.setOnClickListener {
            val fundingLink = viewModel.funding.value?.fundingLink
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip =
                ClipData.newPlainText(getString(R.string.label_funding_link), fundingLink)
            clipboardManager.setPrimaryClip(clip)
            longToast(getString(R.string.label_copy_complete))
        }
        binding.goToPresent.setOnClickListener {
            val fundingTitle = viewModel.funding.value?.name ?: return@setOnClickListener
            PresentActivity.start(this, fundingId, fundingTitle)
        }
        binding.askCloseFunding.setOnClickListener {
            binding.detailActionLayout.visibility = View.GONE
            binding.detailActionLayoutWhenAskingClose.visibility = View.VISIBLE
        }
        binding.keepFundingOpenButton.setOnClickListener {
            binding.detailActionLayout.visibility = View.VISIBLE
            binding.detailActionLayoutWhenAskingClose.visibility = View.GONE
        }
        binding.closeFundingButton.setOnClickListener {
            viewModel.closeFunding()
        }
    }

    companion object {
        fun start(context: Context, fundingId: Int) {
            val intent = Intent(context, FundingDetailActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
            }
            context.startActivity(intent)
        }

        private const val FUNDING_ID = "FUNDING_ID"
    }
}
