package com.thinlineit.favorit_android.android.data.local

interface LocalPreferenceDataSource {
    fun setAccessToken(accessToken: String)

    fun setRefreshToken(refreshToken: String)

    fun getAccessToken(): String?

    fun getRefreshToken(): String?
}
