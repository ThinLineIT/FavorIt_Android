package com.thinlineit.favorit_android.android.ui.present.usecase

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import com.thinlineit.favorit_android.android.data.repository.PresentRepository

class PresentFunding(
    private val presentRepository: PresentRepository
) {
    suspend operator fun invoke(fundingId: Int, request: PresentRequest): Result<PresentResult> =
        presentRepository.present(fundingId, request)
}
