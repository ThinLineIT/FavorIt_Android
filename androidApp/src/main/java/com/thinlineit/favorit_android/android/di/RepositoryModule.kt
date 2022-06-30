package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.api.LoginApi
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSource
import com.thinlineit.favorit_android.android.data.repository.LoginRepository
import com.thinlineit.favorit_android.android.data.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesLoginRepository(
        localPreferenceDataSource: LocalPreferenceDataSource,
        loginApi: LoginApi
    ): LoginRepository = LoginRepositoryImpl(localPreferenceDataSource, loginApi)
}
