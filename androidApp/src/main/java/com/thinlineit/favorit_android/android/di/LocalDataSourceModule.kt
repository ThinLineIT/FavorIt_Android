package com.thinlineit.favorit_android.android.di

import android.content.SharedPreferences
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSource
import com.thinlineit.favorit_android.android.data.local.LocalPreferenceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Provides
    @Singleton
    fun provideLocalPreferenceImpl(localPreference: SharedPreferences): LocalPreferenceDataSource =
        LocalPreferenceDataSourceImpl(localPreference)
}