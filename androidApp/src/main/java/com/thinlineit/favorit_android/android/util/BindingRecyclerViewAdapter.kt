package com.thinlineit.favorit_android.android.util

interface BindingRecyclerViewAdapter<T> {
    fun setData(data: T)
}

interface BindingFragmentStateAdapter<T> {
    fun setData(data: T)
}
