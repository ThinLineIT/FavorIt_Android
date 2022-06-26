package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.thinlineit.favorit_android.android.databinding.CalendarBinding

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val binding: CalendarBinding =
        CalendarBinding.inflate(LayoutInflater.from(context), this, true)
    private val lifecycleOwner by lazy {
        findViewTreeLifecycleOwner() ?: throw Exception("Lifecycle is not exist")
    }
    val viewModel by lazy {
        findViewTreeViewModelStoreOwner()?.let {
            ViewModelProvider(it)[CalendarViewModel::class.java]
        } ?: throw Exception("ViewTree is not exist")
    }
    private val calendarAdapter = CalendarAdapter(context, mutableListOf())

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.lifecycleOwner = lifecycleOwner
        binding.calendarGridView.adapter = calendarAdapter
        binding.viewModel = viewModel.apply {
            dayCellsOfMonth.observe(lifecycleOwner) {
                calendarAdapter.clear()
                calendarAdapter.addAll(it)
                calendarAdapter.notifyDataSetChanged()
            }
        }
    }
}
