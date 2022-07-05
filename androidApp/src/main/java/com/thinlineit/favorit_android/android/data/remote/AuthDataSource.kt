package com.thinlineit.favorit_android.android.data.remote

import com.thinlineit.favorit_android.android.data.entity.Data

interface AuthDataSource {
    suspend fun postLogin(kakaoToken: String): Data
    suspend fun requestAccessToken(refreshToken: String) : Data
}