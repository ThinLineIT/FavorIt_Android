package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.api.FundingApi
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import javax.inject.Inject

class PresentRepositoryImpl @Inject constructor(
    private val fundingApi: FundingApi
) : PresentRepository {

    override suspend fun present(
        fundingId: Int,
        presentRequest: PresentRequest
    ): Result<PresentResult> {
        val presentResponse = fundingApi.presentFunding(fundingId, presentRequest)
        val body = presentResponse.body()
        return if (presentResponse.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            Result.Fail(Exception(presentResponse.message()))
        }
    }
}
