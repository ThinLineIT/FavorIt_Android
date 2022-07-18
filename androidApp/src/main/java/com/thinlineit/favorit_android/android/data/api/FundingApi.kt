package com.thinlineit.favorit_android.android.data.api

import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import com.thinlineit.favorit_android.android.data.entity.ResponseBody
import com.thinlineit.favorit_android.android.data.repository.CreateFundingRequest
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FundingApi {
    @POST("funding")
    suspend fun createFunding(
        @Body request: CreateFundingRequest
    ): Response<ResponseBody<CreateFundingResult>>

    @GET("funding/{funding_id}")
    suspend fun getFunding(
        @Path("funding_id") fundingId: Int
    ): Response<ResponseBody<Funding>>

    @POST("funding/{funding_id}/close")
    suspend fun closeFunding(
        @Path("funding_id") fundingId: Int
    ): Response<ResponseBody<Unit>>

    @POST("funding/{funding_id}/present")
    suspend fun presentFunding(
        @Path("funding_id") fundingId: Int,
        @Body request: PresentRequest
    ): Response<ResponseBody<PresentResult>>
}
