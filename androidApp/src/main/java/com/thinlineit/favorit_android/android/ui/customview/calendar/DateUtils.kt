package com.thinlineit.favorit_android.android.ui.customview.calendar

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val simpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.KOREA)

fun Date.isToday(): Boolean = DateUtils.isToday(this.time)

fun isSameDay(date1: Date, date2: Date): Boolean {
    return simpleDateFormat.format(date1).equals(simpleDateFormat.format(date2))
}

fun Date.laterThanTomorrow(): Boolean {
    return DateUtils.isToday(this.time - DateUtils.DAY_IN_MILLIS) ||
        System.currentTimeMillis() < this.time - DateUtils.DAY_IN_MILLIS
}
