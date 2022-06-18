package com.thinlineit.favorit_android.android.data.api

import com.thinlineit.favorit_android.android.data.entity.LoginRequest
import com.thinlineit.favorit_android.android.data.entity.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("auth/login")
    suspend fun kakaoLogin(
        @Body request: LoginRequest
    ): LoginResponse
}