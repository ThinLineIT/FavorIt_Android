package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.text.format.DateUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarCell.DayCell
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarCell.HeaderCell
import com.thinlineit.favorit_android.android.util.isBetweenDay
import com.thinlineit.favorit_android.android.util.isSameDay
import java.time.Month
import java.util.Calendar
import java.util.Date

class CalendarViewModel : ViewModel() {

    val year by lazy {
        Transformations.map(calendar) {
            it.get(Calendar.YEAR).toString()
        }
    }
    val month by lazy {
        Transformations.map(calendar) {
            Month.of(it.get(Calendar.MONTH) + MONTH_INDEX_ADJUSTER).name
        }
    }

    val endDate = MutableLiveData<Date>()

    val calendarCells: LiveData<List<CalendarCell>> by lazy {
        _calendarCells
    }

    private val calendar by lazy {
        MutableLiveData(Calendar.getInstance())
    }

    private val _calendarCells = MediatorLiveData<List<CalendarCell>>().apply {
        addSource(calendar) {
            value = getCalendarCellsOfMonth(it.clone() as Calendar)
        }
        addSource(endDate) {
            val cells = this.value ?: return@addSource
            value = updateEndDate(cells, it)
        }
    }

    private val startDate = Date(System.currentTimeMillis())

    fun onPreviousClicked() {
        val calendar = calendar.value ?: return
        calendar.add(Calendar.MONTH, ONE_DAY_AGO)
        this.calendar.postValue(calendar)
    }

    fun onNextClicked() {
        val calendar = calendar.value ?: return
        calendar.add(Calendar.MONTH, ONE_DAY_LATER)
        this.calendar.postValue(calendar)
    }

    private fun getCalendarCellsOfMonth(calendar: Calendar): List<CalendarCell> {
        calendar.set(Calendar.DAY_OF_MONTH, FIRST_DAY_OF_MONTH)
        val monthStartCellOffset = calendar.get(Calendar.DAY_OF_WEEK) - DAY_OF_WEEK_OFFSET
        calendar.add(Calendar.DAY_OF_MONTH, -monthStartCellOffset)
        val cells = mutableListOf<CalendarCell>().apply {
            addAll(HEADER_CELLS_LIST)
        }
        while (cells.size < HEADER_CELLS_SIZE + DAY_CELLS_OF_MONTH_MAX_SIZE) {
            cells.add(calendar.mapCurrentDateToDayCell(startDate, endDate.value))
            calendar.add(Calendar.DAY_OF_MONTH, ONE_DAY_LATER)
        }
        return cells
    }

    private fun Calendar.mapCurrentDateToDayCell(startDate: Date, endDate: Date?): DayCell {
        val date = time
        val dayOfMonth = get(Calendar.DAY_OF_MONTH).toString()
        val enabled = !DateUtils.isToday(date.time) || date.after(startDate)
        val isToday = DateUtils.isToday(date.time)
        val isEndDay = endDate?.let {
            date.isSameDay(endDate)
        } ?: false
        val isBetweenDay = endDate?.let {
            date.isBetweenDay(startDate, endDate)
        } ?: false
        val isEndDaySelected = endDate != null
        val onSelected = { _: View ->
            if (date.after(startDate)) {
                onEndDateSelected(date)
            }
        }
        return DayCell(
            date,
            dayOfMonth,
            enabled,
            isToday,
            isEndDay,
            isBetweenDay,
            isEndDaySelected,
            onSelected
        )
    }

    private fun onEndDateSelected(endDate: Date) {
        this.endDate.postValue(endDate)
    }

    private fun updateEndDate(cells: List<CalendarCell>, endDate: Date?) = cells.map { cell ->
        when (cell) {
            is HeaderCell -> cell
            is DayCell -> {
                val isEndDay = endDate?.let { endDate ->
                    cell.date.isSameDay(endDate)
                } ?: false
                val isBetweenDay = endDate?.let { endDate ->
                    cell.date.isBetweenDay(startDate, endDate)
                } ?: false
                cell.copy(
                    isEndDay = isEndDay,
                    isBetweenDay = isBetweenDay,
                    isEndDaySelected = endDate != null
                )
            }
        }
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
        const val ONE_DAY_AGO = -1
        const val ONE_DAY_LATER = 1
        const val FIRST_DAY_OF_MONTH = 1
        const val DAY_OF_WEEK_OFFSET = 1
    }
}
