package com.thinlineit.favorit_android.android.data.repository

import com.google.gson.annotations.SerializedName

data class CreateFundingRequest(
    val name: String,
    @SerializedName("contents")
    val description: String,
    @SerializedName("due_date")
    val expiredDate: String,
    val product: Product
)

data class Product(
    val link: String,
    val option: String,
    val price: Int
)
