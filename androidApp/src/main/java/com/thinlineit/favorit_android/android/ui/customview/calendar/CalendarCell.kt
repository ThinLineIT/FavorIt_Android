package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.view.View
import java.util.Date

sealed class CalendarCell {
    data class HeaderCell(val text: String) : CalendarCell()

    data class DayCell(
        val date: Date,
        val day: String,
        val enabled: Boolean,
        val isToday: Boolean,
        val isEndDay: Boolean,
        val isBetweenDay: Boolean,
        val isEndDaySelected: Boolean,
        val onSelected: (View) -> Unit
    ) : CalendarCell() {
        fun isLeftBackgroundVisible(): Int = when {
            isEndDaySelected && isToday -> View.INVISIBLE
            isEndDaySelected && isBetweenDay -> View.VISIBLE
            isEndDaySelected && isEndDay -> View.VISIBLE
            else -> View.INVISIBLE
        }

        fun isRightBackgroundVisible(): Int = when {
            isEndDaySelected && isToday -> View.VISIBLE
            isEndDaySelected && isBetweenDay -> View.VISIBLE
            isEndDaySelected && isEndDay -> View.INVISIBLE
            else -> View.INVISIBLE
        }
    }
}
