package com.thinlineit.favorit_android.android.ui.createfunding

import android.net.Uri
import android.view.View
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.CreateFundingRequest
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCase
import com.thinlineit.favorit_android.android.util.NumberFormatter
import com.thinlineit.favorit_android.android.util.addSourceList
import com.thinlineit.favorit_android.android.util.laterThanTomorrow
import com.thinlineit.favorit_android.android.util.toDateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateFundingViewModel @Inject constructor(
    private val createFundingUseCase: CreateFundingUseCase
) : ViewModel() {

    val toastMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: MutableLiveData<String> = MutableLiveData("")
    val productLink = MutableLiveData("")
    val fundingPrice = MutableLiveData(0)
    val fundingPriceAsCurrency: LiveData<String> = Transformations.map(fundingPrice) {
        if (it == 0) ""
        else NumberFormatter.asCurrency(it.toLong())
    }
    val fundingPriceAsNumerals: LiveData<String> = Transformations.map(fundingPrice) {
        NumberFormatter.asNumerals(it.toLong())
    }
    val fundingName = MutableLiveData("")
    val fundingDescription = MutableLiveData("")
    val fundingStartDateAsString = Date(System.currentTimeMillis()).toDateFormat()
    val fundingExpiredDate = MutableLiveData<Date?>(null)
    val imageUri = MutableLiveData<Uri>()
    val imageName = MutableLiveData("")

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName

    val fundingImageState: LiveData<InputState> = Transformations.map(imageUri) {
        when (it) {
            null -> InputState.EMPTY
            else -> InputState.AVAILABLE
        }
    }
    val fundingExpiredDateAsString = Transformations.map(fundingExpiredDate) {
        if (it?.toDateFormat() == null) ""
        else (fundingStartDateAsString + "~" + it.toDateFormat()) ?: ""
    }
    val productLinkState: LiveData<InputState> = Transformations.map(productLink) {
        when {
            it.isEmpty() -> InputState.EMPTY
            URLUtil.isNetworkUrl(it) -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    val fundingPriceState: LiveData<InputState> = Transformations.map(fundingPrice) {
        when {
            it == 0 -> InputState.EMPTY
            it > 0 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    val fundingNameState: LiveData<InputState> = Transformations.map(fundingName) {
        when {
            it.isEmpty() -> InputState.EMPTY
            it.length <= 20 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    val fundingDescriptionState: LiveData<InputState> = Transformations.map(fundingDescription) {
        when {
            it.isEmpty() -> InputState.EMPTY
            it.length <= 100 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    val fundingExpiredDateState: LiveData<InputState> = Transformations.map(fundingExpiredDate) {
        when {
            it == null -> InputState.EMPTY
            it.laterThanTomorrow() -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }
    val createFundingState = MediatorLiveData<Boolean>().apply {
        addSourceList(
            fundingImageState,
            fundingExpiredDateState,
            productLinkState,
            fundingPriceState,
            fundingNameState,
            fundingDescriptionState
        ) {
            value = isCreateValid()
        }
    }

    val createFundingResult = MutableLiveData<Result<CreateFundingResult>>(Result.Loading(false))

    val fundingPriceOnNumberClickListener = FundingPriceOnNumberClickListener(
        fundingPrice,
        toastMessage
    )

    fun onEndDateSelected(endDate: Date) {
        fundingExpiredDate.postValue(endDate)
    }

    fun loadImageUrl(uri: Uri, name: String) {
        imageUri.value = uri
        imageName.value = name
    }

    suspend fun createFunding() {
        viewModelScope.launch(Dispatchers.IO) {
            val createFundingRequest = getCreateFundingRequest() ?: run {
                createFundingResult.postValue(Result.Fail(Exception("Some value is null")))
                return@launch
            }
            val result = createFundingUseCase.createFunding(
                createFundingRequest,
                imageUri.value ?: return@launch,
                imageName.value ?: return@launch
            )
            createFundingResult.postValue(result)
        }
    }

    private fun getCreateFundingRequest(): CreateFundingRequest? {
        val name = fundingName.value ?: return null
        val description = fundingDescription.value ?: return null
        val expiredDate = fundingExpiredDate.value ?: return null
        val productLink = productLink.value ?: return null
        val productPrice = fundingPrice.value?.toInt() ?: return null
        return CreateFundingRequest(
            name,
            description,
            expiredDate.toDateFormat(),
            productLink,
            productPrice
        )
    }

    private fun isCreateValid(): Boolean =
        fundingImageState.value == InputState.AVAILABLE &&
                fundingExpiredDateState.value == InputState.AVAILABLE &&
                productLinkState.value == InputState.AVAILABLE &&
                fundingPriceState.value == InputState.AVAILABLE &&
                fundingNameState.value == InputState.AVAILABLE &&
                fundingDescriptionState.value == InputState.AVAILABLE

    sealed class InputState {
        object EMPTY : InputState()
        object AVAILABLE : InputState()
        object UNAVAILABLE : InputState()

        fun toVisibility() = if (this == UNAVAILABLE) {
            View.VISIBLE
        } else {
            View.GONE
        }

        fun toCursorVisibility() = if (this == EMPTY) {
            View.VISIBLE
        } else {
            View.GONE
        }

        fun toEnabled(): Boolean = this == AVAILABLE
    }
}
