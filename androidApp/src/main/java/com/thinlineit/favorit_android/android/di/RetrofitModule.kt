package com.thinlineit.favorit_android.android.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thinlineit.favorit_android.android.data.interceptor.AuthInterceptor
import com.thinlineit.favorit_android.android.data.interceptor.usecase.AuthInterceptorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "https://dev.favorit-be.com/api/"

    @Provides
    @Singleton
    fun providesAuthInterceptor(authInterceptorUseCase: AuthInterceptorUseCase): Interceptor =
        AuthInterceptor(authInterceptorUseCase)

    @Provides
    @Singleton
    fun providesOkhttpClient(authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
