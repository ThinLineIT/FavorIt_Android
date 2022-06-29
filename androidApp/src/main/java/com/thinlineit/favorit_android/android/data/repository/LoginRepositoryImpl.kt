package com.thinlineit.favorit_android.android.data.repository

import android.util.Log
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSource
import com.thinlineit.favorit_android.android.data.remote.AuthDataSource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource,
    private val authDataSource: AuthDataSource
) : LoginRepository {

    override suspend fun login(kakaoToken: String): Boolean = try {
        val data = authDataSource.postLogin(kakaoToken)
        saveAccessToken(data.accessToken)
        saveRefreshToken(data.refreshToken)
        true
    } catch (e: Exception) {
        Log.d(TAG, e.toString())
        false
    }

    override suspend fun requestAccessToken(refreshToken: String): Boolean = try {
        val data = authDataSource.postLogin(refreshToken)
        saveAccessToken(data.accessToken)
        saveRefreshToken(data.refreshToken)
        true
    } catch (e: Exception) {
        Log.d(TAG, e.toString())
        false
    }

    override fun saveAccessToken(accessToken: String) {
        localPreferenceDataSource.setAccessToken(accessToken)
    }

    override fun saveRefreshToken(refreshToken: String) {
        localPreferenceDataSource.setAccessToken(refreshToken)
    }

    companion object {
        const val TAG = "LoginRepository"
    }
}
