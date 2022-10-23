package com.thinlineit.favorit_android.android.ui.fundingList.usecase

import com.thinlineit.favorit_android.android.data.repository.FundingRepository

class GetFundingList(private val fundingRepository: FundingRepository) {
    suspend operator fun invoke() = fundingRepository.getFundingList()
}
