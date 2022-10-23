package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class CreateFundingRequest(
    val name: String,
    @SerializedName("contents")
    val description: String,
    @SerializedName("due_date")
    val expiredDate: String,
    val link: String,
    val price: Int,
)
