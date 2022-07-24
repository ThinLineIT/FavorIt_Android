package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult

interface PresentRepository {
    suspend fun present(fundingId: Int, presentRequest: PresentRequest): Result<PresentResult>
}