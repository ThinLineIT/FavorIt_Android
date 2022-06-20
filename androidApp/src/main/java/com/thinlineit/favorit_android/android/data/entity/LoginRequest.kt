package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("kakao_token")
    val kakaoToken: String
)
