package com.thinlineit.favorit_android.android.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable2.AnimationCallback
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.FundingState
import com.thinlineit.favorit_android.android.databinding.ActivityFundingDetailBinding
import com.thinlineit.favorit_android.android.ui.dialog.BottomUpDialog
import com.thinlineit.favorit_android.android.ui.present.list.PresentListActivity
import com.thinlineit.favorit_android.android.util.longToast
import com.thinlineit.favorit_android.android.util.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        viewModel.fundingStatus.observe(this) {
            when (it) {
                FundingState.OPENED -> {
                    binding.settleButton.setImageResource(R.drawable.icon_box)
                }
                FundingState.EXPIRED -> {
                    binding.settleButton.setImageResource(R.drawable.icon_box)
                }
                FundingState.CLOSED -> {
                    launchFundingCloseAnimAndSetClose()
                }
                FundingState.COMPLETED -> {
                    binding.settleButton.setImageResource(R.drawable.ic_closed_gift_box)
                }
            }
        }
        viewModel.intentLiveData.observe(this) {
            startActivity(it)
        }
        viewModel.funding.observe(this) {
            binding.fundingProgressNameTextView.text = it.name
            binding.fundingProgressBar.setProgress(it.progressPercentage)
        }
        viewModel.fundingExpiredDateString.observe(this) {
            binding.fundingProgressExpiredDateTextView.text =
                Html.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        viewModel.presentStatusString.observe(this) {
            binding.fundingProgressPresentStatusTextView.text =
                Html.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    private fun launchFundingCloseAnimAndSetClose() {
        Glide.with(this)
            .asGif()
            .load(R.drawable.gif_funding_closing) //Your gif resource
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .listener(object : RequestListener<GifDrawable?> {
                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<GifDrawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.setLoopCount(1)
                    resource?.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            super.onAnimationEnd(drawable)
                            binding.settleButton.setImageResource(R.drawable.ic_closed_gift_box)
                        }
                    })
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<GifDrawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(binding.settleButton)
    }

    private fun initView() {
        binding.presentButton.setOnClickListener {
            viewModel.present(this)
        }
        binding.fundingInfoButton.setOnClickListener {
            val funding = viewModel.funding.value ?: return@setOnClickListener
            FundingInfoDialog(binding.root.context, funding).apply {
                show()
            }
        }
        binding.copyFundingLinkButton.setOnClickListener {
            val fundingLink = viewModel.funding.value?.fundingLink ?: return@setOnClickListener
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(getString(R.string.label_funding_link), fundingLink)
            clipboardManager.setPrimaryClip(clip)
            longToast(getString(R.string.label_copy_complete))
        }
        binding.settleButton.setOnClickListener {
            if (viewModel.fundingStatus.value == FundingState.CLOSED) {
                viewModel.settle(this)
            } else {
                PresentListActivity.start(this, viewModel.fundingId)
            }
        }
        binding.closeButton.setOnClickListener {
            if (viewModel.isClosable) {
                BottomUpDialog(this).apply {
                    bodyText = "마감하면 더 이상 선물을 받을 수 없습니다."
                    bodySubText = "펀딩을 마감할까요?"
                    confirmButtonText = "네, 마감할게요!"
                    dismissButtonText = "계속 진행하고 싶어요!"
                    confirmClickListener = {
                        viewModel.close()
                        this.dismiss()
                    }
                    dismissClickListener = {
                        this.dismiss()
                    }
                }.show()
            }
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
