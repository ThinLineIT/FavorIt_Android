package com.thinlineit.favorit_android.android.data.entity

import java.io.File

data class PresentRequest(
    val makerNickName: String,
    val supporterNickName: String,
    val message: String,
    val amount: Int,
    val photo: File
)
