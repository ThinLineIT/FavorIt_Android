package com.thinlineit.favorit_android.android.ui.present

import android.view.View
import androidx.lifecycle.*
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import com.thinlineit.favorit_android.android.ui.present.usecase.PresentUseCase
import com.thinlineit.favorit_android.android.util.NumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PresentViewModel @Inject constructor(
    private val presentUseCase: PresentUseCase,
    state: SavedStateHandle
) : ViewModel() {
    val fundingId: Int =
        state.get<Int>(PresentActivity.FUNDING_ID)
            ?: throw Exception("Funding id is invalid")
    val fundingTitle: String =
        state.get<String>(PresentActivity.FUNDING_TITLE)
            ?: throw Exception("Funding title is invalid")
    val presentPrice = MutableLiveData(0)
    val presentPriceAsCurrency: LiveData<String> = Transformations.map(presentPrice) {
        if (it == 0) ""
        else NumberFormatter.asCurrency(it.toLong())
    }
    val presentPriceAsNumerals: LiveData<String> = Transformations.map(presentPrice) {
        NumberFormatter.asNumerals(it.toLong())
    }

    val presentPriceState: LiveData<InputState> =
        Transformations.map(presentPrice) {
            when {
                it == 0 -> InputState.EMPTY
                it > 0 -> InputState.AVAILABLE
                else -> InputState.UNAVAILABLE
            }
        }

    val presentResult = MutableLiveData<Result<PresentResult>>(Result.Loading(false))

    fun presentFunding() {
        viewModelScope.launch(Dispatchers.IO) {
            presentResult.postValue(Result.Loading(true))
            val presentRequest = getPresentRequest() ?: run {
                presentResult.postValue(Result.Fail(Exception("presentPrice value is null")))
                return@launch
            }
            val result = presentUseCase.presentFunding(fundingId, presentRequest)
            presentResult.postValue(result)
        }
    }

    private fun getPresentRequest(): PresentRequest? {
        val presentPrice = presentPrice.value?.toInt() ?: return null
        return PresentRequest(
            presentPrice
        )
    }

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
