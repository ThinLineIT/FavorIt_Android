package com.thinlineit.favorit_android.android.util

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("fundingTitle", "presentPrice")
fun TextView.setToPresentText(fundingTitle: String, presentPrice: String) {
    val text = "$fundingTitle 에\n $presentPrice 원을 선물했어요"
    this.text = text
}