package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class Bank(
    val text: String,
    val value: String,
    val image: String,
)

data class Account(
    @SerializedName("account_owner_name")
    val accountOwnerName: String
)