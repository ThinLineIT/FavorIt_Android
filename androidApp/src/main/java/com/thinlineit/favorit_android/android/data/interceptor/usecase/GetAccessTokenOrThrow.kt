package com.thinlineit.favorit_android.android.data.interceptor.usecase

import com.thinlineit.favorit_android.android.data.repository.AuthRepository
import javax.inject.Provider

class GetAccessTokenOrThrow(private val authRepositoryProvider: Provider<AuthRepository>) {
    val authRepository: AuthRepository
        get() = authRepositoryProvider.get()

    suspend operator fun invoke(): String =
        authRepository.getAccessToken() ?: throw Exception("Please LogIn first")
}
