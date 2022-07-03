package com.thinlineit.favorit_android.android.ui.createfunding.usecase

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult

class CreateFundingUseCase {
    operator fun invoke(): Result<CreateFundingResult> {
        return Result.Success(CreateFundingResult("fundingLink", "fundingID"))
    }
}
