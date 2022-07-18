package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.repository.FundingRepository
<<<<<<< HEAD
import com.thinlineit.favorit_android.android.data.repository.PresentRepository
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCase
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCases
import com.thinlineit.favorit_android.android.ui.present.usecase.PresentFunding
import com.thinlineit.favorit_android.android.ui.present.usecase.PresentUseCase
=======
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFunding
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCase
import com.thinlineit.favorit_android.android.ui.detail.usecase.CloseFunding
import com.thinlineit.favorit_android.android.ui.detail.usecase.FundingDetailUseCase
import com.thinlineit.favorit_android.android.ui.detail.usecase.GetFunding
>>>>>>> main
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

    @Provides
    @Singleton
    fun providesPresentUseCase(presentRepository: PresentRepository): PresentUseCase =
        PresentUseCase(
            PresentFunding(presentRepository)
        )
}
