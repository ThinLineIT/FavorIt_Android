package com.thinlineit.favorit_android.android.ui.createfunding

import android.view.View
import androidx.lifecycle.*
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

    val productLinkProgressState = MediatorLiveData<ProgressState>().apply {
        addSource(productLinkState) {
            value = createEnum(
                productLinkState.value,
                currentFragment.value == FragmentType.PRODUCT_LINK
            )
        }
        addSource(currentFragment) {
            value = createEnum(
                productLinkState.value,
                currentFragment.value == FragmentType.PRODUCT_LINK
            )
        }
    }
    val productOptionProgressState = MediatorLiveData<ProgressState>().apply {
        addSource(productOptionState) {
            value = createEnum(
                productOptionState.value,
                currentFragment.value == FragmentType.PRODUCT_OPTION
            )
        }
        addSource(currentFragment) {
            value = createEnum(
                productOptionState.value,
                currentFragment.value == FragmentType.PRODUCT_OPTION
            )
        }
    }
    val fundingPriceProgressState = MediatorLiveData<ProgressState>().apply {
        addSource(fundingPriceState) {
            value = createEnum(
                fundingPriceState.value,
                currentFragment.value == FragmentType.FUNDING_PRICE
            )
        }
        addSource(currentFragment) {
            value = createEnum(
                fundingPriceState.value,
                currentFragment.value == FragmentType.FUNDING_PRICE
            )
        }
    }
    val fundingNameProgressState = MediatorLiveData<ProgressState>().apply {
        addSource(fundingNameState) {
            value = createEnum(
                fundingNameState.value,
                currentFragment.value == FragmentType.FUNDING_NAME
            )
        }
        addSource(currentFragment) {
            value = createEnum(
                fundingNameState.value,
                currentFragment.value == FragmentType.FUNDING_NAME
            )
        }
    }
    val fundingDescriptionProgressState = MediatorLiveData<ProgressState>().apply {
        addSource(fundingDescriptionState) {
            value = createEnum(
                fundingDescriptionState.value,
                currentFragment.value == FragmentType.FUNDING_DESCRIPTION
            )
        }
        addSource(currentFragment) {
            value = createEnum(
                fundingDescriptionState.value,
                currentFragment.value == FragmentType.FUNDING_DESCRIPTION
            )
        }
    }
    val fundingExpiredDateProgressState = MediatorLiveData<ProgressState>().apply {
        addSource(fundingExpiredDateState) {
            value = createEnum(
                fundingExpiredDateState.value,
                currentFragment.value == FragmentType.FUNDING_EXPIRED_DATE
            )
        }
        addSource(currentFragment) {
            value = createEnum(
                fundingExpiredDateState.value,
                currentFragment.value == FragmentType.FUNDING_EXPIRED_DATE
            )
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

    fun createEnum(inputState: InputState?, isCurrentFragment: Boolean): ProgressState {
        var enumState: ProgressState = ProgressState.EMPTY
        if (isCurrentFragment) {
            when (inputState) {
                InputState.EMPTY -> enumState = ProgressState.EMPTY
                InputState.AVAILABLE -> enumState = ProgressState.CORRECT_ENTERED
                InputState.UNAVAILABLE -> enumState = ProgressState.EDITING
            }
        } else {
            when (inputState) {
                InputState.EMPTY -> enumState = ProgressState.EMPTY
                InputState.AVAILABLE -> enumState = ProgressState.COMPLETE
                InputState.UNAVAILABLE -> enumState = ProgressState.EDITING
            }
        }
        return enumState
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
        PRODUCT_LINK, PRODUCT_OPTION, FUNDING_PRICE, FUNDING_NAME, FUNDING_DESCRIPTION, FUNDING_EXPIRED_DATE
    }
}
