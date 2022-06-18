package com.thinlineit.favorit_android.android.data.local

interface LocalPreferenceDataSource {
    fun setAccessToken(accessToken: String)

    fun getAccessToken(): String
}