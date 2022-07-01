package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
<<<<<<< HEAD
import com.google.android.material.datepicker.MaterialDatePicker
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingExpiredDateBinding

class EnterFundingExpiredDateFragment : CreateFundingBaseFragment<FragmentEnterFundingExpiredDateBinding>(R.layout.fragment_enter_funding_expired_date)  {
=======
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingExpiredDateBinding

class EnterFundingExpiredDateFragment :
    CreateFundingBaseFragment<FragmentEnterFundingExpiredDateBinding>(R.layout.fragment_enter_funding_expired_date) {
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@EnterFundingExpiredDateFragment.viewModel
            previousButton.setOnClickListener {
                navController.navigate(R.id.action_enterFundingExpiredDateFragment_to_enterFundingDescriptionFragment)
            }
            nextButton.setOnClickListener {
                if (viewModel?.fundingExpiredDateState?.value == CreateFundingViewModel.InputState.AVAILABLE) {
                    navController.navigate(R.id.action_enterFundingExpiredDateFragment_to_fundingPreviewFragment)
                }
            }
<<<<<<< HEAD
=======
            calendarDatePicker.setEndDate(this@EnterFundingExpiredDateFragment.viewModel.fundingExpiredDate.value)
            calendarDatePicker.onEndDateUpdated = {
                this@EnterFundingExpiredDateFragment.viewModel.onEndDateSelected(it)
            }
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
        }
    }
}
