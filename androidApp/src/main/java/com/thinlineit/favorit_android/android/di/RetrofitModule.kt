package com.thinlineit.favorit_android.android.di

<<<<<<< HEAD
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thinlineit.favorit_android.android.data.interceptor.AuthInterceptor
=======
>>>>>>> main
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    const val BASE_URL = "http://3.35.218.213/api/"

    @Provides
    @Singleton
<<<<<<< HEAD
    fun providesAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor
=======
    fun providesInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
>>>>>>> main

    @Provides
    @Singleton
    fun providesOkHttpInterceptor(
        authInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
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
