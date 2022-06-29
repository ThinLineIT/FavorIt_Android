package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class RefreshRequest(
    @SerializedName("refresh_token")
    val refreshToken: String
)
