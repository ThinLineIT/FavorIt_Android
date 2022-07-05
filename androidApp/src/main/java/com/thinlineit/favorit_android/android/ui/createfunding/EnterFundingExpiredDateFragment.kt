package com.thinlineit.favorit_android.android.ui.createfunding

import android.os.Bundle
import android.view.View
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.FragmentEnterFundingExpiredDateBinding
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarView

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
        }
        initCalendarDatePicker(binding.calendarDatePicker)
    }

    private fun initCalendarDatePicker(calendarDatePicker: CalendarView) {
        calendarDatePicker.setEndDate(viewModel.fundingExpiredDate.value)
        calendarDatePicker.onEndDateUpdated = {
            viewModel.onEndDateSelected(it)
        }
    }
}
