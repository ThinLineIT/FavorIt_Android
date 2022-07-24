package com.thinlineit.favorit_android.android.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("app:data")
fun <T> setRecyclerViewData(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindingRecyclerViewAdapter<*> && data != null) {
        (recyclerView.adapter as BindingRecyclerViewAdapter<T>).setData(data)
    }
}

@BindingAdapter("android:imageURI")
fun loadImage(view: ImageView, uri: String) {
    uri.let {
        Glide.with(view.context)
            .load(uri)
            .fitCenter()
            .into(view)
    }
}

@BindingAdapter("fundingTitle", "presentPrice")
fun TextView.setToPresentText(fundingTitle: String, presentPrice: String) {
    val text = "$fundingTitle 에\n $presentPrice 원을 선물했어요"
    this.text = text
}
