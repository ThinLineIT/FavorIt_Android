package com.thinlineit.favorit_android.android.data.repository

import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.api.FundingApi
import com.thinlineit.favorit_android.android.data.entity.Account
import com.thinlineit.favorit_android.android.data.entity.Bank
import com.thinlineit.favorit_android.android.data.entity.CheckBankAccountRequest
import com.thinlineit.favorit_android.android.data.entity.CreateFundingRequest
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.FundingLists
import com.thinlineit.favorit_android.android.data.entity.SettleFundingRequest
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingResult
import javax.inject.Inject

class FundingRepositoryImpl @Inject constructor(
    private val fundingApi: FundingApi
) : FundingRepository {

    override suspend fun create(request: CreateFundingRequest): Result<CreateFundingResult> {
        val createFundingResponse = fundingApi.createFunding(request)
        val body = createFundingResponse.body()
        return if (createFundingResponse.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            Result.Fail(Exception(createFundingResponse.message()))
        }
    }

    override suspend fun getFunding(fundingId: Int): Result<Funding> {
        val response = fundingApi.getFunding(fundingId)
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            Result.Fail(Exception(response.message()))
        }
    }

    override suspend fun closeFunding(fundingId: Int): Result<Unit> {
        val response = fundingApi.closeFunding(fundingId)
        return if (response.isSuccessful) {
            Result.Success(Unit)
        } else {
            Result.Fail(Exception(response.message()))
        }
    }

    override suspend fun getBankList(): List<Bank> {
        return fundingApi.getBankList()
    }

    override suspend fun checkBankAccount(
        bankCode: String,
        bankAccount: String
    ): Result<Account> {
        val response = fundingApi.checkBankAccount(CheckBankAccountRequest(bankCode, bankAccount))
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun settleFunding(settleFundingRequest: SettleFundingRequest): Result<Unit> {
        val response = fundingApi.settleFunding(settleFundingRequest)
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            throw Exception(response.message())
        }
    }

    override suspend fun getFundingList(): Result<FundingLists> {
        val response = fundingApi.getFundingList()
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            Result.Success(body.data)
        } else {
            Result.Fail(Exception(response.message()))
        }
    }
}
