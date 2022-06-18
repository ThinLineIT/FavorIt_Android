package com.thinlineit.favorit_android.android.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.thinlineit.favorit_android.android.data.local.FavoritSharedPreference
import com.thinlineit.favorit_android.android.databinding.ActivityLoginBinding
import com.thinlineit.favorit_android.android.util.observeIfNotHandled
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.apply {
            lifecycleOwner = this@LoginActivity
            this.loginViewModel = viewModel
        }
        initIsLoginSuccessObserver()
        initButtonClickListener()
    }

    private fun initButtonClickListener() {
        binding.kakaoLoginButton.setOnClickListener {
            startKaKaoLogin()
        }
    }

    private fun startKaKaoLogin() {
        getKaKaoToken(viewModel.kakaoLoginCallback)
    }

    //TODO : util 함수로 분리할 것(activity context 넘기는 방식으로)
    private fun getKaKaoToken(callback: (OAuthToken?, Throwable?) -> Unit) {
        val isKakaoLogin = UserApiClient.instance.isKakaoTalkLoginAvailable(this)
        if (isKakaoLogin) {
            Log.d("kakaoLogin", "카카오톡으로 로그인 가능")
            UserApiClient.instance.loginWithKakaoTalk(
                this,
                callback = callback
            )
        } else {
            Log.d("kakaoLogin", "카카오톡으로 로그인 불가능")
            UserApiClient.instance.loginWithKakaoAccount(
                this,
                callback = callback
            )
        }
    }

    private fun initIsLoginSuccessObserver() {
        viewModel.isSuccessLogin.observeIfNotHandled(this) { isSuccess ->
            if (isSuccess) {
                Log.d("LoginActivity","Login Success")
                finish()
            } else {
                Toast.makeText(this, "Login Fail", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun startLoginActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}