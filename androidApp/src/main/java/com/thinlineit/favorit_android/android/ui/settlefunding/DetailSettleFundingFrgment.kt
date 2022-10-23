package com.thinlineit.favorit_android.android.ui.settlefunding

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentFinishSettleFundingDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailSettleFundingFrgment :
    SettleFundingBaseFragment<FragmentFinishSettleFundingDetailBinding>(
        R.layout.fragment_finish_settle_funding_detail
    ) {

    private lateinit var detailSettleFundingPeopleListAdapter: DetailSettleFundingPeopleListAdapter
    private lateinit var peopleList: ArrayList<People>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@DetailSettleFundingFrgment.viewModel
            initView()

            CoroutineScope(Dispatchers.Main).launch {
                delay(1200)

            }
        }
    }

    private fun initView() {
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

        detailSettleFundingPeopleListAdapter = DetailSettleFundingPeopleListAdapter(peopleList)

        binding.apply {
            fundingSettlePeopleList.apply {
                adapter = detailSettleFundingPeopleListAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        }

        animateProgressBar()
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