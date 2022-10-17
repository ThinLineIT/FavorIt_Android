package com.thinlineit.favorit_android.android.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.databinding.ActivityMainBinding
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingActivity
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.ui.fundingList.FundingListActivity
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

        viewModel.isLoggedIn.observe(this) {
            if (it) {
                binding.loginButton.setImageResource(R.drawable.icon_login_profile)
            } else {
                binding.loginButton.setImageResource(R.drawable.icon_login)
            }
        }

        initObserver()
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
            mainLogo.setOnClickListener {
                FundingListActivity.start(this@MainActivity)
            }
        }
    }

    private fun initObserver() {
        viewModel.isLoggedIn.observe(this) {
            logInButtonVisibility(it)
        }
    }

    private fun logInButtonVisibility(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            binding.loginButton.visibility = View.GONE
            binding.profileButton.visibility = View.VISIBLE
        } else {
            binding.loginButton.visibility = View.VISIBLE
            binding.profileButton.visibility = View.GONE
        }
    }

    override fun onResume() {
        viewModel.checkIsLoggedIn()
        super.onResume()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
