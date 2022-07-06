package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.api.AuthApi
import com.thinlineit.favorit_android.android.data.remote.AuthDataSource
import com.thinlineit.favorit_android.android.data.remote.AuthDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    @Singleton
    fun providesAuthDataSource(
        authApi: AuthApi
    ): AuthDataSource = AuthDataSourceImpl(authApi)
}
