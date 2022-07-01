package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
<<<<<<< HEAD
=======
import androidx.lifecycle.MutableLiveData
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.thinlineit.favorit_android.android.databinding.CalendarBinding
<<<<<<< HEAD
=======
import java.util.Date
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val binding: CalendarBinding =
        CalendarBinding.inflate(LayoutInflater.from(context), this, true)
<<<<<<< HEAD
    private val lifecycleOwner by lazy {
        findViewTreeLifecycleOwner() ?: throw Exception("Lifecycle is not exist")
    }
    val viewModel by lazy {
=======

    var onEndDateUpdated: ((Date) -> Unit)? = null

    private val lifecycleOwner by lazy {
        findViewTreeLifecycleOwner() ?: throw Exception("Lifecycle is not exist")
    }

    private val viewModel by lazy {
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
        findViewTreeViewModelStoreOwner()?.let {
            ViewModelProvider(it)[CalendarViewModel::class.java]
        } ?: throw Exception("ViewTree is not exist")
    }
<<<<<<< HEAD
=======

>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
    private val calendarAdapter = CalendarAdapter(context, mutableListOf())

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.lifecycleOwner = lifecycleOwner
        binding.calendarGridView.adapter = calendarAdapter
        binding.viewModel = viewModel.apply {
<<<<<<< HEAD
            dayCellsOfMonth.observe(lifecycleOwner) {
=======
            calendarCells.observe(lifecycleOwner) {
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
                calendarAdapter.clear()
                calendarAdapter.addAll(it)
                calendarAdapter.notifyDataSetChanged()
            }
<<<<<<< HEAD
        }
    }
=======
            endDate.observe(lifecycleOwner) {
                onEndDateUpdated?.invoke(it)
            }
        }
    }

    fun setEndDate(endDate: Date?) {
        endDate?.let {
            viewModel.endDate.postValue(it)
        } ?: return
    }
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
}
