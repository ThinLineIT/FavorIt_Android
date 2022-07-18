package com.thinlineit.favorit_android.android.ui.present

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.databinding.FragmentEnterPresentConfirmBinding
import com.thinlineit.favorit_android.android.util.shortToast

class EnterPresentConfirmFragment :
    PresentBaseFragment<FragmentEnterPresentConfirmBinding>(R.layout.fragment_enter_present_confirm) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initButtonListener()
        initObserver()
    }

    private fun initButtonListener() {
        binding.apply {
            previousButton.setOnClickListener {
                navController.navigate(
                    EnterPresentConfirmFragmentDirections
                        .actionEnterPresentConfirmFragmentToEnterPresentPriceFragment()
                )
            }
            presentButton.setOnClickListener {
                this@EnterPresentConfirmFragment.viewModel.presentFunding()
            }
        }
    }

    private fun initObserver() {
        viewModel.presentResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.presentButton.isEnabled = !result.isLoading
                }
                is Result.Fail -> {
                    binding.presentButton.isEnabled = true
                    requireContext().shortToast("Something is wrong. Please try again.")
                }
                is Result.Success -> {
                    binding.presentButton.isEnabled = true
                    navController.navigate(
                        EnterPresentConfirmFragmentDirections
                            .actionEnterPresentConfirmFragmentToFinishPresentFragment()
                    )
                }
            }
        }
    }
}