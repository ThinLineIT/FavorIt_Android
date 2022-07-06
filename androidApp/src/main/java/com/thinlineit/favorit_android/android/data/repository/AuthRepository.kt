package com.thinlineit.favorit_android.android.data.repository

interface AuthRepository {
    suspend fun login(kakaoToken: String): Boolean
    suspend fun refreshToken(): String?
    suspend fun getAccessToken(): String?
}
