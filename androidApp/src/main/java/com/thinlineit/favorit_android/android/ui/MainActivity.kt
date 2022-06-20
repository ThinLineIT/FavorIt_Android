package com.thinlineit.favorit_android.android.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.util.Utility
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

    //TODO : 4명(윤권,민기,정민,동기) keyHash를 developer에 등록하면 삭제
    private fun getHashKey() {
        var keyHash = Utility.getKeyHash(this)
        Log.d("kakaoHash", keyHash)
    }
}