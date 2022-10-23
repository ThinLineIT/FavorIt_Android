package com.thinlineit.favorit_android.android.ui.present

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.view.View
import androidx.core.content.ContentResolverCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.data.entity.PresentRequest
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import com.thinlineit.favorit_android.android.ui.present.usecase.PresentUseCase
import com.thinlineit.favorit_android.android.util.NumberFormatter
import com.thinlineit.favorit_android.android.util.fileFromContentUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PresentViewModel @Inject constructor(
    private val presentUseCase: PresentUseCase,
    state: SavedStateHandle,
) : ViewModel() {

    val toastMessage: MutableLiveData<String> = MutableLiveData("")

    val fundingId: Int = state.get<Int>(PresentActivity.FUNDING_ID)
        ?: throw Exception("Funding id is invalid")
    val presentPhoto = MutableLiveData<Uri>(null)
    val makerNickName = MutableLiveData("")
    val presentMessage = MutableLiveData("")
    val supporterNickName = MutableLiveData("")
    val presentAmount = MutableLiveData(0)
    val presentAmountAsCurrency: LiveData<String> = Transformations.map(presentAmount) {
        if (it == 0) ""
        else NumberFormatter.asCurrency(it.toLong())
    }
    val presentPriceAsNumerals: LiveData<String> = Transformations.map(presentAmount) {
        NumberFormatter.asNumerals(it.toLong())
    }

    val isPhotoSelected: LiveData<Int> = Transformations.map(presentPhoto) {
        return@map if (it == null) View.VISIBLE else View.GONE
    }

    val makerNickNameState: LiveData<InputState> = Transformations.map(makerNickName) {
        when {
            it.isEmpty() -> InputState.EMPTY
            it.length < 10 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }
    val presentMessageState: LiveData<InputState> = Transformations.map(presentMessage) {
        when {
            it.isEmpty() -> InputState.EMPTY
            it.length < 100 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }
    val supporterNickNameState: LiveData<InputState> = Transformations.map(supporterNickName) {
        when {
            it.isEmpty() -> InputState.EMPTY
            it.length < 10 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    val presentAmountState: LiveData<InputState> = Transformations.map(presentAmount) {
        when {
            it == 0 -> InputState.EMPTY
            it > 0 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    val presentResult = MutableLiveData<Result<PresentResult>>(Result.Loading(false))

    val presentAmountOnNumberClickListener = PresentPriceOnNumberClickListener(
        presentAmount,
        toastMessage
    )

    fun onImageSelected(uri: Uri?) {
        if (uri != null) {
            presentPhoto.postValue(uri)
        }
    }

    fun presentFunding(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            presentResult.postValue(Result.Loading(true))
            val presentRequest = getPresentRequest(context) ?: run {
                presentResult.postValue(Result.Fail(Exception("presentPrice value is null")))
                return@launch
            }
            val result = presentUseCase.presentFunding(fundingId, presentRequest)
            presentResult.postValue(result)
        }
    }

    private fun getPresentRequest(context: Context): PresentRequest? {
        return PresentRequest(
            makerNickName.value ?: return null,
            supporterNickName.value ?: return null,
            presentMessage.value ?: return null,
            presentAmount.value ?: return null,
            fileFromContentUri(context, presentPhoto.value?: return null)
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
