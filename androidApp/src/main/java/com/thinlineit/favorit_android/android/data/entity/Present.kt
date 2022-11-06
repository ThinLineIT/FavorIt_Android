package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

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
) : Serializable
