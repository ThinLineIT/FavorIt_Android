package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.api.FundingApi
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSource
import com.thinlineit.favorit_android.android.data.remote.AuthDataSource
import com.thinlineit.favorit_android.android.data.repository.AuthRepository
import com.thinlineit.favorit_android.android.data.repository.AuthRepositoryImpl
import com.thinlineit.favorit_android.android.data.repository.FundingRepository
import com.thinlineit.favorit_android.android.data.repository.FundingRepositoryImpl
import com.thinlineit.favorit_android.android.data.repository.PresentRepository
import com.thinlineit.favorit_android.android.data.repository.PresentRepositoryImpl
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
        authDataSource: AuthDataSource
    ): AuthRepository = AuthRepositoryImpl(localPreferenceDataSource, authDataSource)

    @Provides
    @Singleton
    fun providesFundingRepository(
        fundingApi: FundingApi
    ): FundingRepository = FundingRepositoryImpl(fundingApi)

    @Provides
    @Singleton
    fun providesPresentRepository(
        fundingApi: FundingApi
    ): PresentRepository = PresentRepositoryImpl(fundingApi)
}
