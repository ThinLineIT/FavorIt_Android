package com.thinlineit.favorit_android.android.ui.settlefunding

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.os.Bundle
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
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
import com.thinlineit.favorit_android.android.databinding.FragmentFinishSettleFundingDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailSettleFundingFrgment : Fragment() {

    private lateinit var binding: FragmentFinishSettleFundingDetailBinding

    private lateinit var detailSettleFundingPeopleListAdapter: DetailSettleFundingPeopleListAdapter
    private lateinit var peopleList: ArrayList<People>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinishSettleFundingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                fundingSettlePeopleList.smoothScrollToPosition(peopleList.size)
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
        val fadeInAnim = AnimationUtils.loadAnimation(requireActivity(), R.anim.fadein)
        view.startAnimation(fadeInAnim)
        view.visibility = View.VISIBLE
    }

    private fun initView() {

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

        peopleList = ArrayList()
        peopleList.add(People("민기"))
        peopleList.add(People("윤권"))
        peopleList.add(People("정민"))
        peopleList.add(People("동기"))
        peopleList.add(People("민기"))
        peopleList.add(People("윤권"))
        peopleList.add(People("정민"))
        peopleList.add(People("동기"))

        detailSettleFundingPeopleListAdapter = DetailSettleFundingPeopleListAdapter(peopleList)

        binding.apply {
            fundingSettlePeopleList.apply {
                adapter = detailSettleFundingPeopleListAdapter

                // VariableScrollSpeedLinearLayoutManager for change scroll speed
                val linearLayoutManager =
                    VariableScrollSpeedLinearLayoutManager(requireActivity(), 8F)
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
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 2 * SECOND_IN_MILLIS
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener { animation ->
            binding.moneyProgressBar.progress = animation.animatedValue as Int
        }
        animator.start()
    }
}