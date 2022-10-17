package com.thinlineit.favorit_android.android.data.entity

import com.google.gson.annotations.SerializedName

data class FundingInfo(
    @SerializedName("funding_id")
    val fundingID: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("due_data")
    val dueDate: String,
    @SerializedName("image")
    val image: String
)

data class FundingList(
    @SerializedName("my_fundings")
    val myFundings: List<FundingInfo>,
    @SerializedName("friends_fundings")
    val friendsFundings: List<FundingInfo>
)
