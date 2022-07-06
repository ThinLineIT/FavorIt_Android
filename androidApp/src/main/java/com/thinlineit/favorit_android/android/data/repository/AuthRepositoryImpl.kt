package com.thinlineit.favorit_android.android.data.repository

import android.util.Log
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSource
import com.thinlineit.favorit_android.android.data.remote.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource,
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun login(kakaoToken: String): Boolean = try {
        val data = authDataSource.postLogin(kakaoToken)
        saveTokens(data.accessToken, data.refreshToken)
        true
    } catch (e: Exception) {
        Log.d(TAG, e.toString())
        false
    }

    override fun saveTokens(accessToken: String, refreshToken: String) {
        localPreferenceDataSource.setAccessToken(accessToken)
        localPreferenceDataSource.setRefreshToken(refreshToken)
    }

    companion object {
        const val TAG = "LoginRepository"
    }
}
