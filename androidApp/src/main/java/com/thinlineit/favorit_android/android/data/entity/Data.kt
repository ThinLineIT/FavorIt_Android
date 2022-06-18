package com.thinlineit.favorit_android.android.data.entity


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("access_token")
    val accessToken: String
)