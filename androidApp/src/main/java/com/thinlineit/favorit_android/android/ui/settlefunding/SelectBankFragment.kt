package com.thinlineit.favorit_android.android.ui.settlefunding

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentSelectBankBinding

class SelectBankFragment :
    SettleFundingBaseFragment<FragmentSelectBankBinding>(R.layout.fragment_select_bank) {
    private val bankListAdapter = BankListAdapter { bank ->
        viewModel.selectBank(bank.value, bank.text)
        this.findNavController().navigate(
            SelectBankFragmentDirections.actionSelectBankFragmentToEnterBankAccountFragment()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@SelectBankFragment.viewModel.apply { loadBankList() }
            bankListRecyclerView.adapter = bankListAdapter
            bankListRecyclerView.layoutManager =
                GridLayoutManager(this@SelectBankFragment.context, 3)
        }
    }
}
