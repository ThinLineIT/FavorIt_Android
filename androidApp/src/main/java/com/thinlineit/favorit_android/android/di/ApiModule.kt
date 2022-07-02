package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesLoginApi(retrofit: Retrofit): LoginApi =
        retrofit.create(LoginApi::class.java)
}
