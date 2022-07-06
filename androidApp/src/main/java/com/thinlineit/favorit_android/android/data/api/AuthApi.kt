package com.thinlineit.favorit_android.android.data.api

import com.thinlineit.favorit_android.android.data.entity.LoginRequest
import com.thinlineit.favorit_android.android.data.entity.RefreshRequest
import com.thinlineit.favorit_android.android.data.entity.ResponseBody
import com.thinlineit.favorit_android.android.data.entity.Tokens
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun kakaoLogin(
        @Body request: LoginRequest
    ): Response<ResponseBody<Tokens>>

    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Body request: RefreshRequest
    ): Response<ResponseBody<Tokens>>
}
