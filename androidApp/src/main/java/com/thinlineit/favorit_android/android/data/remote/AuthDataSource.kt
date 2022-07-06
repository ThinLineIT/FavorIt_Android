package com.thinlineit.favorit_android.android.data.remote

import com.thinlineit.favorit_android.android.data.entity.Tokens

interface AuthDataSource {
    suspend fun login(kakaoToken: String): Tokens?
    suspend fun refreshToken(refreshToken: String): Tokens?
}
