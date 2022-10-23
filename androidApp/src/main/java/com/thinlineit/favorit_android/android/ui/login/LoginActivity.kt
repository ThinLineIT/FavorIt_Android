package com.thinlineit.favorit_android.android.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.ActivityLoginBinding
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.ui.splash.SplashActivity
import com.thinlineit.favorit_android.android.util.observeIfNotHandled
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    lateinit var viewModel: LoginViewModel
    val fundingId: Int by lazy {
        parseFundingId(intent)
    }

    private fun parseFundingId(intent: Intent): Int {
        return if (intent.action == Intent.ACTION_VIEW) {
            try {
                intent.data?.lastPathSegment?.toInt() ?: FundingDetailActivity.INVALID_FUNDING_ID
            } catch (e: Exception) {
                FundingDetailActivity.INVALID_FUNDING_ID
            }
        } else {
            FundingDetailActivity.INVALID_FUNDING_ID
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.apply {
            lifecycleOwner = this@LoginActivity
            this.loginViewModel = viewModel
        }
        initIsLoggedInObserver()
        initIsLoginSuccessObserver()
        initButtonClickListener()
    }

    override fun onResume() {
        viewModel.checkIsLoggedIn()
        super.onResume()
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
        val isKakaoLoginAvailable = UserApiClient.instance.isKakaoTalkLoginAvailable(this)
        if (isKakaoLoginAvailable) {
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
        viewModel.isLoginSuccess.observeIfNotHandled(this) { isSuccess ->
            if (isSuccess) {
                Log.d("LoginActivity", "Login Success")
                startSplash()
            } else {
                Toast.makeText(this, "Login Fail", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initIsLoggedInObserver() {
        viewModel.isLoggedIn.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                if (it) {
                    startSplash()
                } else {
                    binding.kakaoLoginButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun startSplash() {
        binding.kakaoLoginButton.visibility = View.GONE
        binding.root.setBackgroundResource(R.drawable.background_door)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            SplashActivity.start(this@LoginActivity, fundingId)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            finish()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
