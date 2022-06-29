package com.thinlineit.favorit_android.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.databinding.ActivityMainBinding
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingActivity
import com.thinlineit.favorit_android.android.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtonClickListener()
    }

    private fun initButtonClickListener() {
        binding.apply {
            loginButton.setOnClickListener {
                LoginActivity.start(this@MainActivity)
            }
            createFundingButton.setOnClickListener {
                CreateFundingActivity.start(this@MainActivity)
            }
        }
    }
}
