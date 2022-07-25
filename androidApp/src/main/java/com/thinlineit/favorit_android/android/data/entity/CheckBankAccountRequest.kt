package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class CheckBankAccountRequest(
    @SerializedName("bank_code")
    val bankCode: String,
    @SerializedName("account_number")
    val bankAccount: String,
)
