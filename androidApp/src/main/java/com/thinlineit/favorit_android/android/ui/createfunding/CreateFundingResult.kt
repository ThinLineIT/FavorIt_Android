package com.thinlineit.favorit_android.android.ui.createfunding

import com.google.gson.annotations.SerializedName

data class CreateFundingResult(
    @SerializedName("product_link")
    val fundingLink: String,
    @SerializedName("funding_id")
    val fundingID: Int,
)
