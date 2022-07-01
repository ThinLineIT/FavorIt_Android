package com.thinlineit.favorit_android.android.ui.createfunding

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCases
<<<<<<< HEAD
import com.thinlineit.favorit_android.android.util.NumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
=======
import com.thinlineit.favorit_android.android.ui.customview.calendar.laterThanTomorrow
import com.thinlineit.favorit_android.android.util.NumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
import javax.inject.Inject

@HiltViewModel
class CreateFundingViewModel @Inject constructor(
    private val createFundingUseCases: CreateFundingUseCases
) : ViewModel() {
<<<<<<< HEAD

=======
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
    val productLink = MutableLiveData("")
    val productOption = MutableLiveData("")
    val fundingPrice = MutableLiveData(0L)
    val fundingPriceAsCurrency: LiveData<String> = Transformations.map(fundingPrice) {
        if (it == 0L) ""
        else NumberFormatter.asCurrency(it)
    }
    val fundingPriceAsNumerals: LiveData<String> = Transformations.map(fundingPrice) {
        NumberFormatter.asNumerals(it)
    }
    val fundingName = MutableLiveData("")
    val fundingDescription = MutableLiveData("")
<<<<<<< HEAD
    val fundingExpiredDate = MutableLiveData("")

=======
    val fundingExpiredDate = MutableLiveData<Date?>(null)
    val fundingExpiredDateAsString = Transformations.map(fundingExpiredDate) {
        fundingExpiredDate.toString()
    }
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
    val productLinkState: LiveData<InputState> = Transformations.map(productLink) {
        when {
            it.isEmpty() -> InputState.EMPTY
            it.length > 10 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    val productOptionState: LiveData<InputState> = Transformations.map(productOption) {
        when {
            it.isEmpty() -> InputState.EMPTY
            it.length <= 60 -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    val fundingPriceState: LiveData<InputState> = Transformations.map(fundingPrice) {
        when {
            it == 0L -> InputState.EMPTY
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
<<<<<<< HEAD
            it.isEmpty() -> InputState.EMPTY
            it.length > 3 -> InputState.AVAILABLE
=======
            it == null -> InputState.EMPTY
            it.laterThanTomorrow() -> InputState.AVAILABLE
>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
            else -> InputState.UNAVAILABLE
        }
    }

<<<<<<< HEAD
=======
    fun onEndDateSelected(endDate: Date) {
        fundingExpiredDate.postValue(endDate)
    }

>>>>>>> cd737a79c43cb114f75b2c948efe5fbeb95889c9
    sealed class InputState {
        object EMPTY : InputState()
        object AVAILABLE : InputState()
        object UNAVAILABLE : InputState()

        fun toVisibility() = if (this == UNAVAILABLE) {
            View.VISIBLE
        } else {
            View.GONE
        }

        fun toVisibilityCursor() = if (this == EMPTY) {
            View.VISIBLE
        } else {
            View.GONE
        }

        fun toColor(): Int = if (this == UNAVAILABLE) {
            R.color.blue
        } else {
            R.color.lightGray
        }
    }
}
