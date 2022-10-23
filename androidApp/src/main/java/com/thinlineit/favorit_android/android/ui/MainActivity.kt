package com.thinlineit.favorit_android.android.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.databinding.ActivityMainBinding
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingActivity
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.ui.fundingList.FundingListActivity
import com.thinlineit.favorit_android.android.ui.present.PresentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this@MainActivity
        initButtonClickListener()
    }

    private fun initButtonClickListener() {
        binding.apply {
            createFundingButton.setOnClickListener {
                CreateFundingActivity.start(this@MainActivity)
            }
            goToFundingListButton.setOnClickListener {
                FundingListActivity.start(this@MainActivity)
            }
            goToFundingDetailButton.setOnClickListener {
                FundingDetailActivity.start(this@MainActivity, 3)
            }
            goToPresentButton.setOnClickListener {
                PresentActivity.start(this@MainActivity, 95)
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
