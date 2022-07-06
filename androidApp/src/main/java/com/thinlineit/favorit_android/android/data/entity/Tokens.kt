package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class Tokens(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
