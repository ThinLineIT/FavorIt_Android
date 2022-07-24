package com.thinlineit.favorit_android.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.ActivityMainBinding
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingActivity
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.ui.landing.MainViewModel
import com.thinlineit.favorit_android.android.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.apply {
            lifecycleOwner = this@MainActivity
            this.mainViewModel = viewModel
        }

        viewModel.isLoggedIn.observe(this){
            if(it){
                binding.loginButton.setImageResource(R.drawable.icon_login_profile)
            }else{
                binding.loginButton.setImageResource(R.drawable.icon_login)
            }
        }

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
            goToFundingDetailButton.setOnClickListener {
                FundingDetailActivity.start(this@MainActivity, 3)
            }
        }
    }
    override fun onResume() {
        viewModel.checkIsLoggedIn()
        super.onResume()
    }
}
