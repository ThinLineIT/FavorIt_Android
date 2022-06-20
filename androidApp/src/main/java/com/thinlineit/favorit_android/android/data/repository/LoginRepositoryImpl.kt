package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.api.LoginApi
import com.thinlineit.favorit_android.android.data.entity.LoginRequest
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource,
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun login(kakaoToken: String): Boolean = try {
        val loginResponse = loginApi.kakaoLogin(LoginRequest(kakaoToken))
        saveAccessToken(loginResponse.data.accessToken)
        true
    } catch (e: Exception) {
        false
    }

    override fun saveAccessToken(accessToken: String) {
        localPreferenceDataSource.setAccessToken(accessToken)
    }
}