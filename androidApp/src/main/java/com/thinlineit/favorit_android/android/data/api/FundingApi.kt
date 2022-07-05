package com.thinlineit.favorit_android.android.data.api

import com.thinlineit.favorit_android.android.data.entity.ResponseBody
import com.thinlineit.favorit_android.android.data.repository.CreateFundingRequest
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FundingApi {
    @POST("funding")
    suspend fun createFunding(
        @Body request: CreateFundingRequest
    ): Response<ResponseBody<CreateFundingResult>>
}
