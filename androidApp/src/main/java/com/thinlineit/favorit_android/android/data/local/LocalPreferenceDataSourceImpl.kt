package com.thinlineit.favorit_android.android.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class LocalPreferenceDataSourceImpl @Inject constructor(
    private val localPreferences: SharedPreferences
) : LocalPreferenceDataSource {
    override fun setAccessToken(accessToken: String) {
        localPreferences.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .apply()
    }

    override fun getAccessToken() =
        localPreferences.getString(ACCESS_TOKEN, DEFAULT_STRING_VALUE) ?: DEFAULT_STRING_VALUE

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val DEFAULT_STRING_VALUE = ""
    }
}