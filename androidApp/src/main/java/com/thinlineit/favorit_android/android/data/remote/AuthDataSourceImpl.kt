package com.thinlineit.favorit_android.android.data.remote

import com.thinlineit.favorit_android.android.data.api.AuthApi
import com.thinlineit.favorit_android.android.data.entity.LoginRequest
import com.thinlineit.favorit_android.android.data.entity.RefreshRequest
import com.thinlineit.favorit_android.android.data.entity.Tokens
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthDataSource {
    override suspend fun login(kakaoToken: String): Tokens? {
        val response = authApi.kakaoLogin(LoginRequest(kakaoToken))
        return response.body()?.data
    }

    override suspend fun refreshToken(refreshToken: String): Tokens? {
        val response = authApi.refreshToken(RefreshRequest(refreshToken))
        return response.body()?.data
    }
}
