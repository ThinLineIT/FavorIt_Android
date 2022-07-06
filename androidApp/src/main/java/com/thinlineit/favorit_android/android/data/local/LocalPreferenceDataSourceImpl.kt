package com.thinlineit.favorit_android.android.data.local

import javax.inject.Inject

class LocalPreferenceDataSourceImpl @Inject constructor(
    private val localPreferences: FavoritSharedPreference
) : LocalPreferenceDataSource {
    override fun setAccessToken(accessToken: String) {
        localPreferences.setValue(ACCESS_TOKEN, accessToken)
    }

    override fun setRefreshToken(refreshToken: String) {
        localPreferences.setValue(REFRESH_TOKEN, refreshToken)
    }

    override fun getAccessToken(): String? =
        localPreferences.getValue(ACCESS_TOKEN)?.takeIf { it.isNotEmpty() }

    override fun getRefreshToken(): String? =
        localPreferences.getValue(REFRESH_TOKEN)?.takeIf { it.isNotEmpty() }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }
}
