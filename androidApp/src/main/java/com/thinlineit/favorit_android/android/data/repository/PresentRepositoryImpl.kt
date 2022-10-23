package com.thinlineit.favorit_android.android.data.repository

import android.content.ContentResolver
import androidx.core.net.toFile
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.api.FundingApi
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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
}
