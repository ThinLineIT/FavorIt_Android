package com.thinlineit.favorit_android.android.data.interceptor.usecase

data class AuthInterceptorUseCase(
    val getAccessTokenOrThrow: GetAccessTokenOrThrow,
    val getRefreshedAccessTokenOrThrow: GetRefreshedAccessTokenOrThrow
)
