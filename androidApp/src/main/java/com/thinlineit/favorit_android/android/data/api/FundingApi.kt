package com.thinlineit.favorit_android.android.data.api

import com.thinlineit.favorit_android.android.data.entity.Account
import com.thinlineit.favorit_android.android.data.entity.Bank
import com.thinlineit.favorit_android.android.data.entity.CheckBankAccountRequest
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.FundingLists
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import com.thinlineit.favorit_android.android.data.entity.ResponseBody
import com.thinlineit.favorit_android.android.data.entity.SettleFundingRequest
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult
import com.thinlineit.favorit_android.android.data.entity.Present
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FundingApi {
    @Multipart
    @POST("v2/funding")
    suspend fun createFunding(
        @Part name: MultipartBody.Part,
        @Part contents: MultipartBody.Part,
        @Part due_date: MultipartBody.Part,
        @Part link: MultipartBody.Part,
        @Part price: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): Response<ResponseBody<CreateFundingResult>>

    @GET("funding/{funding_id}")
    suspend fun getFunding(
        @Path("funding_id") fundingId: Int
    ): Response<ResponseBody<Funding>>

    @GET("fundings")
    suspend fun getFundingList(): Response<ResponseBody<FundingLists>>

    @POST("funding/{funding_id}/close")
    suspend fun closeFunding(
        @Path("funding_id") fundingId: Int
    ): Response<ResponseBody<Unit>>

    @Multipart
    @POST("v2/funding/{funding_id}/present")
    suspend fun presentFunding(
        @Path("funding_id") fundingId: Int,
        @Part from_name: MultipartBody.Part,
        @Part to_name: MultipartBody.Part,
        @Part amount: MultipartBody.Part,
        @Part contents: MultipartBody.Part,
        @Part image: MultipartBody.Part,
    ): Response<ResponseBody<PresentResult>>

    @GET("funding/options/bank")
    suspend fun getBankList(): List<Bank>

    @POST("funding/verification/bank-account")
    suspend fun checkBankAccount(
        @Body request: CheckBankAccountRequest
    ): Response<ResponseBody<Account>>

    @POST("funding/{funding_id}/payment")
    suspend fun settleFunding(
        @Body request: SettleFundingRequest
    ): Response<ResponseBody<Unit>>

    @GET("funding/{funding_id}/presents")
    suspend fun listPresent(
        @Path("funding_id") fundingId: Int
    ): Response<ResponseBody<List<Present>>>
}
