package com.thinlineit.favorit_android.android.data.repository

interface LoginRepository {
    suspend fun login(kakaoToken : String): Boolean
    suspend fun requestAccessToken(refreshToken : String): Boolean
    fun saveAccessToken(accessToken : String)
    fun saveRefreshToken(refreshToken : String)
}