package com.thinlineit.favorit_android.android.data.repository

interface AuthRepository {
    suspend fun login(kakaoToken: String): Boolean
    fun saveTokens(accessToken: String, refreshToken: String)
}
