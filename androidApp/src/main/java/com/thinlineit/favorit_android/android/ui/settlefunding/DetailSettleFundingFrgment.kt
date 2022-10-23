package com.thinlineit.favorit_android.android.ui.settlefunding

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentFinishSettleFundingDetailBinding

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
        }
    }

    private fun initView() {
        binding.goToBank.setOnClickListener {
            navController.navigate(
                DetailSettleFundingFrgmentDirections
                    .actionDetailFundingSettleFragmentToClosedFundingFragment()
            )
        }

        peopleList = ArrayList()
        peopleList.add(People("민기"))
        peopleList.add(People("윤권"))
        peopleList.add(People("정민"))
        peopleList.add(People("동기"))

        detailSettleFundingPeopleListAdapter = DetailSettleFundingPeopleListAdapter(peopleList)

        binding.fundingSettlePeopleList.apply {
            adapter = detailSettleFundingPeopleListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }
}