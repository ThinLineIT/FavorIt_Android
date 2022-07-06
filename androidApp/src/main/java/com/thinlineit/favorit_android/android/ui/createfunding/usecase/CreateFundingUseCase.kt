package com.thinlineit.favorit_android.android.ui.createfunding.usecase

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.repository.CreateFundingRequest
import com.thinlineit.favorit_android.android.data.repository.FundingRepository
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult

class CreateFundingUseCase(
    private val fundingRepository: FundingRepository
) {
    suspend operator fun invoke(request: CreateFundingRequest): Result<CreateFundingResult> =
        fundingRepository.create(request)
}
