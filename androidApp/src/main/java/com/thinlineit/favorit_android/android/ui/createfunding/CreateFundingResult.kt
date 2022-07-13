package com.thinlineit.favorit_android.android.ui.createfunding

import com.google.gson.annotations.SerializedName

data class CreateFundingResult(
    @SerializedName("link_for_sharing")
    val fundingLink: String,
    @SerializedName("funding_id")
    val fundingID: Int,
)
