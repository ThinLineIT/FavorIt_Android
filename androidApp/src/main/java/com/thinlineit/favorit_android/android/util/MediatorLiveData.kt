package com.thinlineit.favorit_android.android.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> MediatorLiveData<T>.addSourceList(
    vararg liveDataArgument: LiveData<*>,
    onChange: () -> Unit
) {
    liveDataArgument.forEach {
        this.addSource(it) { onChange() }
    }
}
