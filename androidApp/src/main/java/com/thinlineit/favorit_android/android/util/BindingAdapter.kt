package com.thinlineit.favorit_android.android.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thinlineit.favorit_android.android.di.GlideApp

@BindingAdapter("app:data")
fun <T> setRecyclerViewData(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindingRecyclerViewAdapter<*> && data != null) {
        (recyclerView.adapter as BindingRecyclerViewAdapter<T>).setData(data)
    }
}

@BindingAdapter("android:imageURI")
fun loadImage(view: ImageView, uri: String) {
    uri.let {
        GlideApp.with(view.context)
            .load(uri)
            .fitCenter()
            .into(view)
    }
}
