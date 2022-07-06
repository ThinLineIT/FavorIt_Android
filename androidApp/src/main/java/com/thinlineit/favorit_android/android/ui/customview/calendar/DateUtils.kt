package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val simpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

fun Date.isSameDay(date: Date): Boolean =
    simpleDateFormat.format(this).equals(simpleDateFormat.format(date))

fun Date.isBetweenDay(startDate: Date, endDate: Date): Boolean =
    !isSameDay(startDate) && after(startDate) && before(endDate) && !isSameDay(endDate)

fun Date.laterThanTomorrow(): Boolean {
    return DateUtils.isToday(this.time - DateUtils.DAY_IN_MILLIS) ||
        System.currentTimeMillis() < this.time - DateUtils.DAY_IN_MILLIS
}

fun Date.toDateFormat(): String {
    return dateFormat.format(this)
}
