package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.api.AuthApi
import com.thinlineit.favorit_android.android.data.api.FundingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun providesFundingApi(retrofit: Retrofit): FundingApi =
        retrofit.create(FundingApi::class.java)
}
