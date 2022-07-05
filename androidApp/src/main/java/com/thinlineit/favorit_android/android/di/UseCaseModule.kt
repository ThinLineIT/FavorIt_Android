package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.repository.FundingRepository
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCase
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCases
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
    fun providesCreateFundingUseCase(fundingRepository: FundingRepository): CreateFundingUseCases =
        CreateFundingUseCases(
            CreateFundingUseCase(fundingRepository)
        )
}
