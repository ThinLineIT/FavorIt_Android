package com.thinlineit.favorit_android.android.ui.createfunding

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.ui.createfunding.usecase.CreateFundingUseCases
import com.thinlineit.favorit_android.android.ui.customview.ProgressButtons.ProgressState
import com.thinlineit.favorit_android.android.ui.customview.calendar.laterThanTomorrow
import com.thinlineit.favorit_android.android.util.NumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateFundingViewModel @Inject constructor(
    private val createFundingUseCases: CreateFundingUseCases
) : ViewModel() {
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
    val fundingExpiredDate = MutableLiveData<Date?>(null)
    val fundingExpiredDateAsString = Transformations.map(fundingExpiredDate) {
        fundingExpiredDate.toString()
    }
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
            it == null -> InputState.EMPTY
            it.laterThanTomorrow() -> InputState.AVAILABLE
            else -> InputState.UNAVAILABLE
        }
    }

    fun onEndDateSelected(endDate: Date) {
        fundingExpiredDate.postValue(endDate)
    }

    val currentFragment = MutableLiveData(FragmentType.PRODUCT_LINK)

    private val productLinkProgressState =
        createProgressStateMediatorLiveData(
            productLinkState,
            currentFragment,
            FragmentType.PRODUCT_LINK
        )

    private val productOptionProgressState =
        createProgressStateMediatorLiveData(
            productOptionState,
            currentFragment,
            FragmentType.PRODUCT_OPTION
        )

    private val fundingPriceProgressState =
        createProgressStateMediatorLiveData(
            fundingPriceState,
            currentFragment,
            FragmentType.FUNDING_PRICE
        )
    private val fundingNameProgressState =
        createProgressStateMediatorLiveData(
            fundingNameState,
            currentFragment,
            FragmentType.FUNDING_NAME
        )

    private val fundingDescriptionProgressState =
        createProgressStateMediatorLiveData(
            fundingDescriptionState,
            currentFragment,
            FragmentType.FUNDING_DESCRIPTION
        )

    private val fundingExpiredDateProgressState =
        createProgressStateMediatorLiveData(
            fundingExpiredDateState,
            currentFragment,
            FragmentType.FUNDING_EXPIRED_DATE
        )

    private fun createProgressStateMediatorLiveData(
        inputState: LiveData<InputState>,
        currentFragment: LiveData<FragmentType>,
        fragmentType: FragmentType
    ): MediatorLiveData<ProgressState> = MediatorLiveData<ProgressState>().apply {
        addSource(inputState) {
            value = createProgressState(it, currentFragment.value == fragmentType)
        }
        addSource(currentFragment) {
            value = createProgressState(inputState.value, currentFragment.value == fragmentType)
        }
    }

    val progressStateList: List<MediatorLiveData<ProgressState>> = listOf(
        productLinkProgressState,
        productOptionProgressState,
        fundingPriceProgressState,
        fundingNameProgressState,
        fundingDescriptionProgressState,
        fundingExpiredDateProgressState
    )

    private fun createProgressState(
        inputState: InputState?,
        isCurrentFragment: Boolean
    ): ProgressState = when {
        !isCurrentFragment && inputState != InputState.AVAILABLE -> ProgressState.EMPTY
        isCurrentFragment && inputState != InputState.AVAILABLE -> ProgressState.EDITING
        isCurrentFragment && inputState == InputState.AVAILABLE -> ProgressState.CORRECT_ENTERED
        !isCurrentFragment && inputState == InputState.AVAILABLE -> ProgressState.COMPLETE
        else -> throw Exception("Something is wrong")
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

    enum class FragmentType {
        PRODUCT_LINK, PRODUCT_OPTION, FUNDING_PRICE,
        FUNDING_NAME, FUNDING_DESCRIPTION, FUNDING_EXPIRED_DATE
    }
}
