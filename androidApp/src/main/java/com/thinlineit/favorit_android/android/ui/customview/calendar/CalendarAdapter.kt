package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.ItemViewCalendarDayCellBinding
import com.thinlineit.favorit_android.android.databinding.ItemViewCalendarHeaderCellBinding

class CalendarAdapter(
    context: Context,
    private val cells: List<CalendarCell>
) : ArrayAdapter<CalendarCell>(context, R.layout.item_view_calendar_day_cell, cells) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return when (val calendarCell = cells[position]) {
            is CalendarCell.HeaderCell -> getHeaderCellView(calendarCell, convertView, parent)
            is CalendarCell.DayCell -> getDayCellView(calendarCell, convertView, parent)
        }
    }

    private fun getHeaderCellView(
        headerCell: CalendarCell.HeaderCell,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val binding = if (convertView == null) {
            ItemViewCalendarHeaderCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).apply {
                root.tag = this
            }
        } else {
            convertView.tag as ItemViewCalendarHeaderCellBinding
        }

        binding.headerCell = headerCell
        return binding.root
    }

    private fun getDayCellView(
        dayCell: CalendarCell.DayCell,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val binding = if (convertView == null) {
            ItemViewCalendarDayCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).apply {
                root.tag = this
            }
        } else {
            convertView.tag as ItemViewCalendarDayCellBinding
        }

        binding.dayCell = dayCell
        if (dayCell.isToday) {
            binding.dayTextView.background =
                AppCompatResources.getDrawable(context, R.drawable.icon_circle_button)
        } else if (dayCell.isEndDay) {
            binding.dayTextView.background =
                AppCompatResources.getDrawable(context, R.drawable.icon_circle_button_end)
        } else if (dayCell.isBetweenDay) {
            binding.dayTextView.background =
                AppCompatResources.getDrawable(context, R.drawable.icon_circle_button_between)
        } else {
            binding.dayTextView.background = null
        }

        binding.root.setOnClickListener(dayCell.onSelected)

        return binding.root
    }

    override fun getItemViewType(position: Int): Int {
        return when (cells[position]) {
            is CalendarCell.HeaderCell -> HEADER_CELL
            is CalendarCell.DayCell -> DAY_CELL
        }
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    companion object {
        private const val HEADER_CELL = 0
        private const val DAY_CELL = 1
    }
}
