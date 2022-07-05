package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.api.FundingApi
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult
import javax.inject.Inject

class FundingRepositoryImpl @Inject constructor(
    private val fundingApi: FundingApi
) : FundingRepository {

    override suspend fun create(request: CreateFundingRequest): Result<CreateFundingResult> {
        val createFundingResponse = fundingApi.createFunding(request)
        val body = createFundingResponse.body()
        return if (createFundingResponse.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            Result.Fail(Exception(createFundingResponse.message()))
        }
    }
}
