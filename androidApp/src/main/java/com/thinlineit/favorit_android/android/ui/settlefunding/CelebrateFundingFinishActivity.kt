package com.thinlineit.favorit_android.android.ui.settlefunding

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.text.format.DateUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.Present
import com.thinlineit.favorit_android.android.databinding.ActivityCelebrateFundingFinishBinding
import com.thinlineit.favorit_android.android.util.NumberFormatter
import com.thinlineit.favorit_android.android.util.calcDDay
import com.thinlineit.favorit_android.android.util.toDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class CelebrateFundingFinishActivity : AppCompatActivity() {

    private val binding: ActivityCelebrateFundingFinishBinding by lazy {
        ActivityCelebrateFundingFinishBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@CelebrateFundingFinishActivity
        }
    }

    private lateinit var detailSettleFundingPeopleListAdapter: DetailSettleFundingPeopleListAdapter
    private lateinit var presentList: ArrayList<Present>
    private var fundingId: Int = 0
    private lateinit var funding: Funding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fundingId = intent.getIntExtra(FUNDING_ID, 0)
        funding = intent.getSerializableExtra(FUNDING) as Funding
        presentList = intent.getSerializableExtra(PRESENT_LIST) as ArrayList<Present>
        initView()
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
            binding.apply {
                fadeInView(fundingSettleBackgroundImageView)
                delay(1000)

                fadeInView(fundingSettlePeopleTagTextView)
                delay(1000)

                fadeInView(fundingSettlePeopleList)
                delay(1000)
                // scroll RecyclerView
                fundingSettlePeopleList.smoothScrollToPosition(presentList.size)
                delay(1000)

                fadeInView(fundingSettleDayTagTextView)
                delay(1000)

                fadeInView(moneyProgressBarLayout)
                delay(1000)

                animateProgressBar()
                delay(2500)

                fadeInView(settleFundingButton)
            }
        }
    }

    private fun fadeInView(view: View) {
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fadein)
        view.startAnimation(fadeInAnim)
        view.visibility = View.VISIBLE
    }

    private fun initView() {
        binding.apply {
            fundingSettlePeopleTagTextView.text = "${presentList.size} 명이 선물해 줬어요"

            val to = Date(System.currentTimeMillis())
            val from = funding.startDate.toDate()
            fundingSettleDayTagTextView.text = "${calcDDay(from!!, to)} 일간의 펀딩이 완료되었습니다."

            targetAmountTextView.text =
                NumberFormatter.asCurrency(funding.product.price.toLong())

            var achievedAmount = 0
            for (present in presentList) achievedAmount += present.amount

            achievedAmountTextView.text =
                NumberFormatter.asCurrency(achievedAmount.toLong())
        }

        // play gif once
        Glide.with(this)
            .asGif()
            .load(R.drawable.funding_settle_box_opening)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.setLoopCount(1)
                    return false
                }

            })
            .into(binding.boxImageView)

        binding.settleFundingButton.setOnClickListener {
            SettleFundingActivity.start(this, fundingId, funding.name)
        }
        detailSettleFundingPeopleListAdapter = DetailSettleFundingPeopleListAdapter(presentList)

        binding.apply {
            fundingSettlePeopleList.apply {
                adapter = detailSettleFundingPeopleListAdapter

                // VariableScrollSpeedLinearLayoutManager for change scroll speed
                val linearLayoutManager =
                    VariableScrollSpeedLinearLayoutManager(
                        this@CelebrateFundingFinishActivity,
                        8F
                    )
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                this.layoutManager = linearLayoutManager
            }
        }
    }

    class VariableScrollSpeedLinearLayoutManager(context: Context?, private val factor: Float) :
        LinearLayoutManager(context) {
        override fun smoothScrollToPosition(
            recyclerView: RecyclerView,
            state: RecyclerView.State,
            position: Int
        ) {
            val linearSmoothScroller: LinearSmoothScroller =
                object : LinearSmoothScroller(recyclerView.context) {
                    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {

                        return this@VariableScrollSpeedLinearLayoutManager
                            .computeScrollVectorForPosition(
                                targetPosition
                            )
                    }

                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                        return super.calculateSpeedPerPixel(displayMetrics) * factor
                    }
                }
            linearSmoothScroller.targetPosition = position
            startSmoothScroll(linearSmoothScroller)
        }
    }


    private fun animateProgressBar() {
        val animator = ValueAnimator.ofInt(0, funding.progressPercentage)
        animator.duration = 2 * DateUtils.SECOND_IN_MILLIS
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener { animation ->
            binding.moneyProgressBar.setProgress(animation.animatedValue as Int)
        }
        animator.start()
    }


    companion object {
        const val FUNDING_ID = "FUNDING_ID"
        const val FUNDING = "funding"
        const val PRESENT_LIST = "presentList"

        fun start(
            context: Context,
            fundingId: Int,
            funding: Funding,
            presentList: ArrayList<Present>
        ) {
            val intent = getIntent(context, fundingId, funding, presentList)
            context.startActivity(intent)
        }

        fun getIntent(
            context: Context,
            fundingId: Int,
            funding: Funding,
            presentList: ArrayList<Present>
        ): Intent {
            return Intent(context, CelebrateFundingFinishActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
                putExtra(FUNDING, funding)
                putExtra(PRESENT_LIST, presentList)
            }
        }
    }
}