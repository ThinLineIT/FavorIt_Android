package com.thinlineit.favorit_android.android.ui.present.usecase

import android.net.Uri
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.repository.PresentRepository
import com.thinlineit.favorit_android.android.data.entity.Present

class GetPresentList(private val presentRepository: PresentRepository) {
    suspend operator fun invoke(fundingId: Int): Result<List<Present>> {
        return presentRepository.listPresent(fundingId)
    }
}
