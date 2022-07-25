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
            val fundingId =
                parseFundingId(intent).takeIf { it != INVALID_FUNDING_ID } ?: return@apply
            loadFundingDetail(fundingId)
        }
    }

    private fun parseFundingId(intent: Intent): Int {
        return if (intent.action == Intent.ACTION_VIEW) {
            try {
                intent.data?.lastPathSegment?.toInt() ?: INVALID_FUNDING_ID
            } catch (e: Exception) {
                INVALID_FUNDING_ID
            }
        } else {
            intent.getIntExtra(FUNDING_ID, INVALID_FUNDING_ID)
        }.takeIf {
            it != INVALID_FUNDING_ID
        } ?: this@FundingDetailActivity.run {
            shortToast("Funding ID is invalid")
            finish()
            return INVALID_FUNDING_ID
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            lifecycleOwner = this@FundingDetailActivity
            viewModel = this@FundingDetailActivity.viewModel
        }
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.loadFundingResult.observe(this) {
            when (it) {
                is Result.Loading -> {
                    // nothing to do
                }
                is Result.Fail -> {
                    shortToast("Fail to load funding")
                    finish()
                }
                is Result.Success -> {
                    // nothing to do
                }
            }
        }

        viewModel.closeFundingResult.observe(this) {
            when (it) {
                is Result.Loading -> {
                    // nothing to do
                }
                is Result.Fail -> {
                    shortToast("Fail to close funding")
                }
                is Result.Success -> {
                    binding.detailActionLayout.visibility = View.VISIBLE
                    binding.detailActionLayoutWhenAskingClose.visibility = View.GONE
                }
            }
        }

        viewModel.showExpiredAlertDialog.observe(this) { showExpiredAlertDialog ->
            if (showExpiredAlertDialog) {
                ExpiredAlertDialog().show(supportFragmentManager, "ExpiredAlert")
            }
        }

        viewModel.goToClosedFundingActivity.observe(this) { goToClosedFundingActivity ->
            if (goToClosedFundingActivity) {
                ClosedFundingActivity.start(this, viewModel.fundingId)
            }
        }
    }

    private fun initView() {
        binding.goToSeeProductTextView.setOnClickListener {
            val productLink = viewModel.funding.value?.product?.link
                ?: run {
                    shortToast("ProductLink is invalid")
                    return@setOnClickListener
                }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(productLink))
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
            PresentActivity.start(this, viewModel.fundingId, fundingTitle)
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

        const val FUNDING_ID = "FUNDING_ID"
        const val INVALID_FUNDING_ID = -1
    }
}
