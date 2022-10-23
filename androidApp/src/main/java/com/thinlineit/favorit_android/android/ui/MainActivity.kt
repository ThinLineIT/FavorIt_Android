package com.thinlineit.favorit_android.android.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.databinding.ActivityMainBinding
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingActivity
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
        initButtonClickListener()
    }

    private fun initButtonClickListener() {
        binding.apply {
            createFundingButton.setOnClickListener {
                CreateFundingActivity.start(this@MainActivity)
            }
            goToFundingListButton.setOnClickListener {
                //ToDo : go to FundingList
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
