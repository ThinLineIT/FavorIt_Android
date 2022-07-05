package com.thinlineit.favorit_android.android.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.thinlineit.favorit_android.android.data.repository.LoginRepository
import com.thinlineit.favorit_android.android.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _isLoginSuccess = MutableLiveData<Event<Boolean>>()
    val isLoginSuccess: LiveData<Event<Boolean>>
        get() = _isLoginSuccess

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (token != null) {
            Log.d("kakaoLogin", "카카오계정 로그인 성공")
            tryLogin(token.accessToken)
        } else if (error != null) {
            Log.d("kakaoLogin", error.toString())
            initLoginState(false)
        }
    }

    private fun tryLogin(kakaoToken: String) {
        viewModelScope.launch {
            try {
                if (loginRepository.login(kakaoToken))
                    initLoginState(true)
                else
                    initLoginState(false)
            } catch (e: Exception) {
                initLoginState(false)
                Log.e("test", e.toString())
            }
        }
    }

    private fun initLoginState(isSuccess: Boolean) {
        _isLoginSuccess.value = Event(isSuccess)
    }
}
