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
            requestWithToken(originalRequest)
        val response = chain.proceed(authRequest)
        when (response.code) {
            401 -> {
                val body = FormBody.Builder().add(
                    REFRESH_TOKEN,
                    localPreferenceDataSource.getRefreshToken()
                ) as RequestBody
                val refreshTokenRequest = requestRefreshToken(originalRequest, body)
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
                    val newRequest = requestWithToken(originalRequest)

                    return chain.proceed(newRequest)
                }
            }
        }
        return response
    }

    private fun requestWithToken(originalRequest: Request): Request =
        originalRequest.newBuilder()
            .addHeader(
                "Authorization",
                "Bearer${localPreferenceDataSource.getAccessToken()}"
            )
            .build()


    private fun requestRefreshToken(originalRequest: Request, body: RequestBody): Request =
        originalRequest.newBuilder().get()
            .url("${RetrofitModule.BASE_URL}auth/refresh_token")
            .post(body)
            .build()

    private fun isAuth(originalRequest: Request) =
        originalRequest.url.encodedPath.contains(AUTH)

    companion object {
        const val REFRESH_TOKEN = "refresh_token"
        const val AUTH = "auth"
    }

}
