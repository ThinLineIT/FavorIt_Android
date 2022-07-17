package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.repository.FundingRepository
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFunding
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCase
import com.thinlineit.favorit_android.android.ui.detail.usecase.CloseFunding
import com.thinlineit.favorit_android.android.ui.detail.usecase.FundingDetailUseCase
import com.thinlineit.favorit_android.android.ui.detail.usecase.GetFunding
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
    fun providesCreateFundingUseCase(fundingRepository: FundingRepository): CreateFundingUseCase =
        CreateFundingUseCase(
            CreateFunding(fundingRepository)
        )

    @Provides
    @Singleton
    fun providesFundingDetailUseCase(fundingRepository: FundingRepository): FundingDetailUseCase =
        FundingDetailUseCase(
            GetFunding(fundingRepository),
            CloseFunding(fundingRepository)
        )
}
