package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.text.format.DateUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarCell.DayCell
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarCell.HeaderCell
import java.time.Month
import java.util.Calendar
import java.util.Date

class CalendarViewModel : ViewModel() {
    val showingMonthAndYear = MutableLiveData(Date())

    val year = MutableLiveData("2022")
    val month = MutableLiveData("June")
    val dayCellsOfMonth = MutableLiveData<List<CalendarCell>>(HEADER_CELLS_LIST)
    val startDate = Date(System.currentTimeMillis())
    val endDate = MutableLiveData<Date>(null)
    private val calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }

    fun onPreviousClicked() {
        calendar.add(Calendar.MONTH, -1)
        updateLiveData()
    }

    fun onNextClicked() {
        calendar.add(Calendar.MONTH, 1)
        updateLiveData()
    }

    private fun updateLiveData() {
        year.postValue(calendar.get(Calendar.YEAR).toString())
        val currentMonth = calendar.get(Calendar.MONTH)
        this.month.postValue(Month.of(currentMonth + 1).name)

        val calendar = calendar.clone() as Calendar
        val monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

        val cells = mutableListOf<CalendarCell>().apply {
            addAll(HEADER_CELLS_LIST)
        }
        while (cells.size < HEADER_CELLS_SIZE + DAY_CELLS_OF_MONTH_MAX_SIZE) {
            val date = calendar.time
            val day = calendar.get(Calendar.DAY_OF_MONTH).toString()
            val enabled = !date.before(startDate)
            val isToday = DateUtils.isToday(date.time)
            val isEndDay = endDate.value != null
            val isSelected = endDate.value?.let { endDate ->
                date.after(startDate) || date.before(endDate)
            } ?: false
            val onSelected = { _: View ->
                if (date.after(startDate)) {
                    onEndDateSelected(date)
                }
            }
            cells.add(
                DayCell(
                    date,
                    day,
                    enabled,
                    isToday,
                    isEndDay,
                    isSelected,
                    onSelected
                )
            )
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        dayCellsOfMonth.postValue(cells)
    }

    private fun onEndDateSelected(endDate: Date) {
        this.endDate.postValue(endDate)
        updateLiveData()
    }

    companion object {
        private val HEADER_CELLS_LIST = listOf(
            HeaderCell("S"),
            HeaderCell("M"),
            HeaderCell("T"),
            HeaderCell("W"),
            HeaderCell("T"),
            HeaderCell("F"),
            HeaderCell("S")
        )
        const val HEADER_CELLS_SIZE = 7
        const val DAY_CELLS_OF_MONTH_MAX_SIZE = 42
    }
}
