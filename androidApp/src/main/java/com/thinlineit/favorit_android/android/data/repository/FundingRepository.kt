package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult

interface FundingRepository {
    suspend fun create(request: CreateFundingRequest): Result<CreateFundingResult>
}
