package com.thinlineit.favorit_android.android.data.repository

import android.util.Log
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSource
import com.thinlineit.favorit_android.android.data.remote.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource,
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun login(kakaoToken: String): Boolean {
        return try {
            val tokens = authDataSource.login(kakaoToken) ?: return false
            saveTokens(tokens.accessToken, tokens.refreshToken)
            true
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
            false
        }
    }

    override suspend fun refreshToken(): String? {
        val refreshToken = localPreferenceDataSource.getRefreshToken()
        val tokens = authDataSource.refreshToken(refreshToken) ?: return null
        saveTokens(tokens.accessToken, tokens.refreshToken)
        return tokens.accessToken
    }

    override suspend fun getAccessToken(): String? {
        return localPreferenceDataSource.getAccessToken()
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        localPreferenceDataSource.setAccessToken(accessToken)
        localPreferenceDataSource.setRefreshToken(refreshToken)
    }

    companion object {
        const val TAG = "LoginRepository"
    }
}
