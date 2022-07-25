package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class SettleFundingRequest(
    @SerializedName("funding_id")
    val fundingId: Int,
    @SerializedName("bank_code")
    val backCode: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("account_number")
    val accountNumber: String
)
