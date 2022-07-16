package com.thinlineit.favorit_android.android.ui.detail.usecase

import com.thinlineit.favorit_android.android.data.repository.FundingRepository

class CloseFunding(private val fundingRepository: FundingRepository) {
    suspend operator fun invoke(fundingId: Int) = fundingRepository.closeFunding(fundingId)
}
