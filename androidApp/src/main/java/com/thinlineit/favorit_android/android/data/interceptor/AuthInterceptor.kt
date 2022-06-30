package com.thinlineit.favorit_android.android.data.interceptor

import com.google.gson.Gson
import com.thinlineit.favorit_android.android.data.entity.LoginResponse
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSource
import com.thinlineit.favorit_android.android.di.RetrofitModule
import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource,
    private val gson: Gson
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = if (isAuth(originalRequest)) originalRequest else
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer${localPreferenceDataSource.getAccessToken()}")
                .build()
        val response = chain.proceed(authRequest)
        when (response.code) {
            401 -> {
                val body = FormBody.Builder().add(
                    "refresh_token",
                    localPreferenceDataSource.getRefreshToken()
                ) as RequestBody
                val refreshTokenRequest = originalRequest.newBuilder().get()
                    .url("${RetrofitModule.BASE_URL}auth/refresh_token")
                    .post(body)
                    .build()
                val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                if (refreshTokenResponse.isSuccessful) {
                    val refreshToken = gson.fromJson(
                        refreshTokenResponse.body?.string(),
                        LoginResponse::class.java
                    )
                    with(localPreferenceDataSource) {
                        setAccessToken(refreshToken.data.accessToken)
                        setRefreshToken(refreshToken.data.refreshToken)
                    }
                    val newRequest = originalRequest.newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer${localPreferenceDataSource.getAccessToken()}"
                        )
                        .build()
                    return chain.proceed(newRequest)
                }
            }
        }
        return response
    }

    private fun isAuth(originalRequest: Request) =
        originalRequest.url.encodedPath.contains("auth")

}
