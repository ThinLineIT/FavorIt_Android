package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.text.format.DateUtils
import android.view.View
<<<<<<< HEAD
import androidx.lifecycle.MutableLiveData
=======
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
import androidx.lifecycle.ViewModel
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarCell.DayCell
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarCell.HeaderCell
import java.time.Month
import java.util.Calendar
import java.util.Date

class CalendarViewModel : ViewModel() {
<<<<<<< HEAD
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
=======
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
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
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
<<<<<<< HEAD
            val isEndDay = endDate.value != null
            val isSelected = endDate.value?.let { endDate ->
                date.after(startDate) || date.before(endDate)
=======
            val isEndDay = endDate.value?.let {
                isSameDay(it, date)
            } ?: false
            val isBetweenDay = endDate.value?.let { endDate ->
                !isEndDay && (date.after(startDate) && date.before(endDate))
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
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
<<<<<<< HEAD
                    isSelected,
=======
                    isBetweenDay,
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
                    onSelected
                )
            )
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
<<<<<<< HEAD
        dayCellsOfMonth.postValue(cells)
=======
        return cells
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
    }

    private fun onEndDateSelected(endDate: Date) {
        this.endDate.postValue(endDate)
<<<<<<< HEAD
        updateLiveData()
=======
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
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
<<<<<<< HEAD
=======
        const val MONTH_INDEX_ADJUSTER = 1
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
    }
}
