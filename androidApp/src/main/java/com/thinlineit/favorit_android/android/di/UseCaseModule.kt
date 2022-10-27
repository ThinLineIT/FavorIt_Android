package com.thinlineit.favorit_android.android.di

import com.thinlineit.favorit_android.android.data.interceptor.usecase.AuthInterceptorUseCase
import com.thinlineit.favorit_android.android.data.interceptor.usecase.GetAccessTokenOrThrow
import com.thinlineit.favorit_android.android.data.interceptor.usecase.GetRefreshedAccessTokenOrThrow
import com.thinlineit.favorit_android.android.data.repository.AuthRepository
import com.thinlineit.favorit_android.android.data.repository.FundingRepository
import com.thinlineit.favorit_android.android.data.repository.PresentRepository
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFunding
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCase
import com.thinlineit.favorit_android.android.ui.detail.usecase.CloseFunding
import com.thinlineit.favorit_android.android.ui.detail.usecase.FundingDetailUseCase
import com.thinlineit.favorit_android.android.ui.detail.usecase.GetFunding
import com.thinlineit.favorit_android.android.ui.fundingList.usecase.FundingListUseCase
import com.thinlineit.favorit_android.android.ui.fundingList.usecase.GetFundingList
import com.thinlineit.favorit_android.android.ui.present.usecase.GetPresentList
import com.thinlineit.favorit_android.android.ui.present.usecase.PresentFunding
import com.thinlineit.favorit_android.android.ui.present.usecase.PresentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providesAuthInterceptorUseCase(
        authRepositoryProvider: Provider<AuthRepository>
    ): AuthInterceptorUseCase =
        AuthInterceptorUseCase(
            GetAccessTokenOrThrow(authRepositoryProvider),
            GetRefreshedAccessTokenOrThrow(authRepositoryProvider)
        )

    @Provides
    @Singleton
    fun providesCreateFundingUseCase(fundingRepository: FundingRepository): CreateFundingUseCase =
        CreateFundingUseCase(
            CreateFunding(fundingRepository)
        )

    @Provides
    @Singleton
    fun providesFundingDetailUseCase(
        fundingRepository: FundingRepository,
        presentRepository: PresentRepository
    ): FundingDetailUseCase =
        FundingDetailUseCase(
            GetFunding(fundingRepository),
            CloseFunding(fundingRepository),
            GetPresentList(presentRepository)
        )

    @Provides
    @Singleton
    fun providesPresentUseCase(presentRepository: PresentRepository): PresentUseCase =
        PresentUseCase(
            PresentFunding(presentRepository)
        )

    @Provides
    @Singleton
    fun providesFundingListUseCase(fundingRepository: FundingRepository): FundingListUseCase =
        FundingListUseCase(
            GetFundingList(fundingRepository)
        )

    @Provides
    @Singleton
    fun providesPresentListUseCase(presentRepository: PresentRepository): GetPresentList =
        GetPresentList(presentRepository)
}
