package com.thinlineit.favorit_android.android.ui.detail.usecase

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.repository.FundingRepository

class GetFunding(private val fundingRepository: FundingRepository) {
    suspend operator fun invoke(fundingId: Int): Result<Funding> =
        fundingRepository.getFunding(fundingId)
}
