package com.thinlineit.favorit_android.android.data.interceptor

import com.thinlineit.favorit_android.android.data.interceptor.usecase.AuthInterceptorUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authInterceptorUseCase: AuthInterceptorUseCase
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.takeIf { !isAccessTokenRequired(it) }
            ?: originalRequest.appendToken(getAccessTokenOrThrow())
        val response = chain.proceed(request)

        if (response.code == UNAUTHORIZED_CODE && !isAuth(originalRequest)) {
            val refreshedAccessToken = getRefreshedAccessTokenOrThrow()
            val requestWithRefreshedToken = chain.request().appendToken(refreshedAccessToken)
            response.close()
            return chain.proceed(requestWithRefreshedToken)
        }
        return response
    }

    private fun getAccessTokenOrThrow(): String = runBlocking {
        authInterceptorUseCase.getAccessTokenOrThrow()
    }

    private fun getRefreshedAccessTokenOrThrow(): String = runBlocking {
        authInterceptorUseCase.getRefreshedAccessTokenOrThrow()
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

    private fun isAuth(originalRequest: Request) =
        originalRequest.url.encodedPath.contains("auth")

    companion object {
        private const val UNAUTHORIZED_CODE = 401
        const val IS_ACCESS_TOKEN_REQUIRED = "isAccessTokenRequired"
    }
}
