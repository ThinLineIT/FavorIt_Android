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
        val originalRequest = chain.request()
        val request = originalRequest.takeIf { !isAccessTokenRequired(it) }
            ?: originalRequest.appendToken(getAccessTokenOrThrow())
        val response = chain.proceed(request)

        if (response.code == UNAUTHORIZED_CODE) {
            val refreshedAccessToken = getRefreshedAccessTokenOrThrow()
            val requestWithRefreshedToken = chain.request().appendToken(refreshedAccessToken)
            response.close()
            return chain.proceed(requestWithRefreshedToken)
        }
        return response
    }

    /**
     * TODO: Extract this method as UseCase
     */
    private fun getAccessTokenOrThrow(): String = runBlocking {
        authRepository.getAccessToken() ?: throw Exception("Please LogIn first")
    }

    /**
     * TODO: Extract this method as UseCase
     */
    private fun getRefreshedAccessTokenOrThrow(): String = runBlocking {
        authRepository.refreshToken() ?: throw Exception("Please LogIn first")
    }

    private fun Request.appendToken(accessToken: String): Request =
        newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $accessToken"
            )
            .build()

    private fun isAccessTokenRequired(request: Request) =
        request.headers[IS_ACCESS_TOKEN_REQUIRED] == null ||
            request.headers[IS_ACCESS_TOKEN_REQUIRED] == "true"

    companion object {
        private const val UNAUTHORIZED_CODE = 401
        const val IS_ACCESS_TOKEN_REQUIRED = "isAccessTokenRequired"
    }
}
