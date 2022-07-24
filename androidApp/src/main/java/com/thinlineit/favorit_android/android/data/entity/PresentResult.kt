package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class PresentResult(
    @SerializedName("link_for_sharing")
    val fundingLink: String,
    @SerializedName("funding_id")
    val fundingID: Int
)
