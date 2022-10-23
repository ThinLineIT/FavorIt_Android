package com.thinlineit.favorit_android.android.ui.present.list

import com.google.gson.annotations.SerializedName

data class Present(
    @SerializedName("to_name")
    val makerNickName: String,
    @SerializedName("from_name")
    val supporterNickName: String,
    @SerializedName("contents")
    val message: String,
    @SerializedName("image")
    val photo: String,
    val amount: Int
)
