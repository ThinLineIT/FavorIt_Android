package com.thinlineit.favorit_android.android.ui.detail.usecase

import com.thinlineit.favorit_android.android.ui.present.usecase.GetPresentList

data class FundingDetailUseCase(
    val getFunding: GetFunding,
    val closeFunding: CloseFunding,
    val getPresentList: GetPresentList
)
