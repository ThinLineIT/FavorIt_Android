package com.thinlineit.favorit_android.android.data.entity

data class ResponseBody<T>(
    val data: T,
    val message: String
)
