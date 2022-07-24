package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult

interface FundingRepository {
    suspend fun create(request: CreateFundingRequest): Result<CreateFundingResult>
    suspend fun getFunding(fundingId: Int): Result<Funding>
    suspend fun closeFunding(fundingId: Int): Result<Unit>
}
