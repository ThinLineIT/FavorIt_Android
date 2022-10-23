package com.thinlineit.favorit_android.android.ui.createfunding.usecase

import android.net.Uri
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.CreateFundingRequest
import com.thinlineit.favorit_android.android.data.repository.FundingRepository
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult
import java.io.File

class CreateFunding(
    private val fundingRepository: FundingRepository
) {
    suspend operator fun invoke(request: CreateFundingRequest, uri: Uri, fileName: String): Result<CreateFundingResult> =
        fundingRepository.create(request, uri, fileName)
}
