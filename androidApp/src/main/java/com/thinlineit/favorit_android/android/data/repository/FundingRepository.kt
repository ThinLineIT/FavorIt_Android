package com.thinlineit.favorit_android.android.data.repository

import android.net.Uri
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.Account
import com.thinlineit.favorit_android.android.data.entity.Bank
import com.thinlineit.favorit_android.android.data.entity.CreateFundingRequest
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.FundingLists
import com.thinlineit.favorit_android.android.data.entity.SettleFundingRequest
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult
import java.io.File

interface FundingRepository {
    suspend fun create(request: CreateFundingRequest, uri: Uri, fileName: String): Result<CreateFundingResult>
    suspend fun getFunding(fundingId: Int): Result<Funding>
    suspend fun closeFunding(fundingId: Int): Result<Unit>
    suspend fun getBankList(): List<Bank>
    suspend fun checkBankAccount(bankCode: String, bankAccount: String): Result<Account>
    suspend fun settleFunding(settleFundingRequest: SettleFundingRequest): Result<Unit>
    suspend fun getFundingList(): Result<FundingLists>
}
