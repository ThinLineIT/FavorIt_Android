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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CelebrateFundingFinishActivity : AppCompatActivity() {

    private val binding: ActivityCelebrateFundingFinishBinding by lazy {
        ActivityCelebrateFundingFinishBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@CelebrateFundingFinishActivity
        }
    }

    private lateinit var detailSettleFundingPeopleListAdapter: DetailSettleFundingPeopleListAdapter
    private lateinit var peopleList: ArrayList<People>

    private lateinit var presentList: ArrayList<Present>
    private lateinit var funding: Funding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        funding = intent.getSerializableExtra("funding") as Funding
        presentList = intent.getSerializableExtra("presentList") as ArrayList<Present>

        initView()
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

                fadeInView(goToBank)
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
            fundingSettlePeopleTagTextView.text = presentList.size.toString() + " 명이 선물해 줬어요"
            // 일자 확인 후 연산 해서 일수 측정 필요
            fundingSettleDayTagTextView.text = funding.expiredDate
            // 000, 000 형태로 변환 필요
            targetAmountTextView.text = "목표금액은 " + funding.product.price.toString() + " 원"

            var achievedAmount = 0
            for (present in presentList) achievedAmount += present.amount

            achievedAmountTextView.text = achievedAmount.toString() + "원을 모았어요"

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

        binding.goToBank.setOnClickListener {
            /*navController.navigate(
                DetailSettleFundingFrgmentDirections
                    .actionDetailFundingSettleFragmentToClosedFundingFragment()
            )*/
        }
        detailSettleFundingPeopleListAdapter = DetailSettleFundingPeopleListAdapter(presentList)


        /*peopleList = ArrayList()
        peopleList.add(People("민기"))
        peopleList.add(People("윤권"))
        peopleList.add(People("정민"))
        peopleList.add(People("동기"))
        peopleList.add(People("민기"))
        peopleList.add(People("윤권"))
        peopleList.add(People("정민"))
        peopleList.add(People("동기"))*/

        //

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
            binding.moneyProgressBar.progress = animation.animatedValue as Int
        }
        animator.start()
    }


    companion object {
        const val FUNDING_ID = "fundingId"
        const val FUNDING_NAME = "fundingName"

        const val FUNDING = "funding"
        const val PRESENT_LIST = "presentList"

        fun start(context: Context, funding: Funding, presentList: ArrayList<Present>) {
            val intent = getIntent(context, funding, presentList)
            context.startActivity(intent)
        }

        fun getIntent(context: Context, funding: Funding, presentList: ArrayList<Present>): Intent {
            return Intent(context, CelebrateFundingFinishActivity::class.java).apply {
                putExtra(FUNDING, funding)
                putExtra(PRESENT_LIST, presentList)
            }
        }
    }
}