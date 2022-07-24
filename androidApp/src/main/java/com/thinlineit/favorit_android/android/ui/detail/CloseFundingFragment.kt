package com.thinlineit.favorit_android.android.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentCloseFundingBinding
import com.thinlineit.favorit_android.android.ui.settlefunding.SettleFundingActivity

class CloseFundingFragment : Fragment() {
    lateinit var binding: FragmentCloseFundingBinding
    val viewModel by lazy {
        ViewModelProvider(requireActivity())[FundingDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate<FragmentCloseFundingBinding>(
                inflater,
                R.layout.fragment_close_funding,
                container,
                false
            )
                .apply {
                    lifecycleOwner = viewLifecycleOwner
                    viewModel = this@CloseFundingFragment.viewModel
                }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            goToAdjustment.setOnClickListener {
                val funding = this@CloseFundingFragment.viewModel.funding.value ?: run {
                    return@setOnClickListener
                }
                SettleFundingActivity.start(requireContext(), funding.name)
            }
        }
    }
}