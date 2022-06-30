package com.thinlineit.favorit_android.android.util

import java.text.DecimalFormat

object NumberFormatter {
    private val decimalFormat = DecimalFormat("#,###")

    fun asCurrency(number: Long): String = decimalFormat.format(number)
    fun asNumerals(number: Long): String {
        return number.toString() + "원"
    }
}
