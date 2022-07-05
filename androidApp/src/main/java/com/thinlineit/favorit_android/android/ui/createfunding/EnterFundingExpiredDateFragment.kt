package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingExpiredDateBinding

class EnterFundingExpiredDateFragment :
    CreateFundingBaseFragment<FragmentEnterFundingExpiredDateBinding>(R.layout.fragment_enter_funding_expired_date) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@EnterFundingExpiredDateFragment.viewModel.apply {
                currentFragment.value = CreateFundingViewModel.FragmentType.FUNDING_EXPIRED_DATE
            }.also {
                progressButtons.setProgressState(it.progressStateList)
            }
            previousButton.setOnClickListener {
                navController.navigate(
                    EnterFundingExpiredDateFragmentDirections
                        .actionEnterFundingExpiredDateFragmentToEnterFundingDescriptionFragment()
                )
            }
            nextButton.setOnClickListener {
                navController.navigate(
                    EnterFundingExpiredDateFragmentDirections
                        .actionEnterFundingExpiredDateFragmentToFundingPreviewFragment()
                )
            }
            calendarDatePicker.setEndDate(this@EnterFundingExpiredDateFragment.viewModel.fundingExpiredDate.value)
            calendarDatePicker.onEndDateUpdated = {
                this@EnterFundingExpiredDateFragment.viewModel.onEndDateSelected(it)
            }
        }
    }
}
