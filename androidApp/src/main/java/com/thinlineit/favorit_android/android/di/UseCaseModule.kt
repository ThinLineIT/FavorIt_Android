package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providesCreateFundingUseCase(): CreateFundingUseCase = CreateFundingUseCase()
}
