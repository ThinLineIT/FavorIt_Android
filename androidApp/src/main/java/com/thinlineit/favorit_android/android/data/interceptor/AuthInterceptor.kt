package com.thinlineit.favorit_android.android.data.interceptor

import com.thinlineit.favorit_android.android.data.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val authRepositoryProvider: Provider<AuthRepository>
) : Interceptor {
    private val authRepository
        get() = authRepositoryProvider.get()

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken =
            runBlocking { authRepository.getAccessToken() ?: throw Exception("Please LogIn first") }
        val requestWithToken = chain.request().appendToken(accessToken)
        val response = chain.proceed(requestWithToken)

        if (response.code == UNAUTHORIZED_CODE) {
            val refreshedAccessToken = runBlocking { authRepository.refreshToken() }
                ?: throw Exception("Please LogIn first")
            val requestWithRefreshedToken = chain.request().appendToken(refreshedAccessToken)
            return chain.proceed(requestWithRefreshedToken)
        }
        return response
    }

    private fun Request.appendToken(accessToken: String): Request =
        this.newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $accessToken"
            )
            .build()

    companion object {
        private const val UNAUTHORIZED_CODE = 401
    }
}
