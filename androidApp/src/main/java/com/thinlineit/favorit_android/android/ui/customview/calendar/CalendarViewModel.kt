package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.text.format.DateUtils
import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarCell.DayCell
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarCell.HeaderCell
import java.time.Month
import java.util.*

class CalendarViewModel : ViewModel() {
    private val calendar = MutableLiveData(Calendar.getInstance())

    val year = Transformations.map(calendar) {
        it.get(Calendar.YEAR).toString()
    }
    val month = Transformations.map(calendar) {
        Month.of(it.get(Calendar.MONTH) + MONTH_INDEX_ADJUSTER).name
    }

    private val startDate = Date(System.currentTimeMillis())
    val endDate = MutableLiveData<Date>()

    val calendarCells = MediatorLiveData<List<CalendarCell>>().apply {
        addSource(calendar) {
            value = getCalendarCells(it.clone() as Calendar)
        }
        addSource(endDate) {
            val cells = this.value ?: return@addSource
            value = updateEndDate(cells, it)
        }
    }

    private fun updateEndDate(cells: List<CalendarCell>, endDate: Date) = cells.map {
        when (it) {
            is HeaderCell -> {
                it
            }
            is DayCell -> {
                val isEndDay = isSameDay(endDate, it.date)
                val isBetweenDay =
                    !isEndDay && (it.date.after(startDate) && it.date.before(endDate))
                it.copy(
                    isEndDay = isEndDay,
                    isBetweenDay = isBetweenDay
                )
            }
        }
    }

    fun onPreviousClicked() {
        val calendar = calendar.value ?: return
        calendar.add(Calendar.MONTH, -1)
        this.calendar.postValue(calendar)
    }

    fun onNextClicked() {
        val calendar = calendar.value ?: return
        calendar.add(Calendar.MONTH, 1)
        this.calendar.postValue(calendar)
    }

    private fun getCalendarCells(calendar: Calendar): List<CalendarCell> {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
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
            val isEndDay = endDate.value?.let {
                isSameDay(it, date)
            } ?: false
            val isBetweenDay = endDate.value?.let { endDate ->
                !isEndDay && (date.after(startDate) && date.before(endDate))
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
                    isBetweenDay,
                    onSelected
                )
            )
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return cells
    }

    private fun onEndDateSelected(endDate: Date) {
        this.endDate.postValue(endDate)
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
        const val MONTH_INDEX_ADJUSTER = 1
    }
}
