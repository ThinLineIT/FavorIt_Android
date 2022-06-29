package com.thinlineit.favorit_android.android.data.remote

import com.thinlineit.favorit_android.android.data.api.LoginApi
import com.thinlineit.favorit_android.android.data.entity.Data
import com.thinlineit.favorit_android.android.data.entity.LoginRequest
import com.thinlineit.favorit_android.android.data.entity.RefreshRequest
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi
) : AuthDataSource {
    override suspend fun postLogin(kakaoToken: String): Data =
        loginApi.kakaoLogin(LoginRequest(kakaoToken)).data

    override suspend fun requestAccessToken(refreshToken: String): Data =
        loginApi.getAccessToken(RefreshRequest(refreshToken)).data
}
