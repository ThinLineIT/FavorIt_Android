package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.api.FundingApi
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import com.thinlineit.favorit_android.android.data.entity.Present
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

class PresentRepositoryImpl @Inject constructor(
    private val fundingApi: FundingApi
) : PresentRepository {

    override suspend fun present(
        fundingId: Int,
        presentRequest: PresentRequest
    ): Result<PresentResult> {
        val presentResponse = fundingApi.presentFunding(
            fundingId,
            MultipartBody.Part.createFormData(
                "amount", presentRequest.amount.toString()
            ),
            MultipartBody.Part.createFormData(
                "from_name", presentRequest.supporterNickName
            ),
            MultipartBody.Part.createFormData(
                "to_name", presentRequest.makerNickName
            ),
            MultipartBody.Part.createFormData(
                "contents", presentRequest.message
            ),
            MultipartBody.Part.createFormData(
                "image",
                filename = presentRequest.photo.name,
                body = presentRequest.photo.asRequestBody("image/*".toMediaType())
            )
        )
        val body = presentResponse.body()
        return if (presentResponse.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            Result.Fail(Exception(presentResponse.message()))
        }
    }

    override suspend fun listPresent(fundingId: Int): Result<List<Present>> {
        val response = fundingApi.listPresent(fundingId)
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            Result.Fail(Exception(response.message()))
        }
    }
}
