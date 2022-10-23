package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PresentResult(
    @SerializedName("funding_name")
    val fundingName: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("link_for_sharing")
    val fundingLink: String,
    @SerializedName("funding_id")
    val fundingID: Int
) : Serializable
