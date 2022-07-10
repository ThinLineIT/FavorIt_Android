package com.thinlineit.favorit_android.android.data.api

import com.thinlineit.favorit_android.android.data.entity.LoginRequest
import com.thinlineit.favorit_android.android.data.entity.RefreshRequest
import com.thinlineit.favorit_android.android.data.entity.ResponseBody
import com.thinlineit.favorit_android.android.data.entity.Tokens
import com.thinlineit.favorit_android.android.data.interceptor.AuthInterceptor.Companion.IS_ACCESS_TOKEN_REQUIRED
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("$IS_ACCESS_TOKEN_REQUIRED: false")
    @POST("auth/login")
    suspend fun kakaoLogin(
        @Body request: LoginRequest
    ): Response<ResponseBody<Tokens>>

    @Headers("$IS_ACCESS_TOKEN_REQUIRED: false")
    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Body request: RefreshRequest
    ): Response<ResponseBody<Tokens>>
}
