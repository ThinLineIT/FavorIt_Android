package com.thinlineit.favorit_android.android.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinlineit.favorit_android.android.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val accessToken = MutableLiveData<String>()

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    fun checkAccessToken(){
        viewModelScope.launch {
            try {
                accessToken.value = authRepository.getAccessToken()
                _isLoggedIn.value = accessToken.value != null
            } catch (e: Exception){

            }
        }
    }

    init {
        checkAccessToken()
    }
}