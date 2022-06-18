package com.thinlineit.favorit_android.android.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.thinlineit.favorit_android.Greeting
import com.thinlineit.favorit_android.android.databinding.ActivityMainBinding
import com.thinlineit.favorit_android.android.ui.login.LoginActivity

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtonClickListener()
        getHashKey()
    }

    private fun initButtonClickListener() {
        binding.apply {
            loginButton.setOnClickListener {
                LoginActivity.startLoginActivity(this@MainActivity)
            }
        }
    }

    private fun getHashKey(){
        var keyHash = Utility.getKeyHash(this)
        Log.d("kakaoHash", keyHash)
    }
}