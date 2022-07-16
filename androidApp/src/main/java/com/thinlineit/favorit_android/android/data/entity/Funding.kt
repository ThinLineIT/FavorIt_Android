package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class Funding(
    val name: String,
    @SerializedName("contents")
    val description: String,
    val state: FundingState,
    @SerializedName("is_maker")
    val isMaker: Boolean,
    @SerializedName("due_date")
    val expiredDate: String,
    @SerializedName("progress_percent")
    val progressPercentage: Int,
    @SerializedName("link_for_sharing")
    val fundingLink: String,
    val product: Product,
)

enum class FundingState {
    OPENED, EXPIRED, CLOSED, COMPLETED
}

data class Product(
    val link: String,
    val option: String,
    val price: Int,
)
