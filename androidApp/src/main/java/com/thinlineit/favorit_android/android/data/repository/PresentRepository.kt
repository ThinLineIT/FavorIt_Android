package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import com.thinlineit.favorit_android.android.ui.present.list.Present

interface PresentRepository {
    suspend fun present(fundingId: Int, presentRequest: PresentRequest): Result<PresentResult>
    suspend fun listPresent(fundingId: Int): Result<List<Present>>
}