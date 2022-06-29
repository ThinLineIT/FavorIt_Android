package com.thinlineit.favorit_android.android.ui.createfunding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFundingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_funding)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CreateFundingActivity::class.java)
            context.startActivity(intent)
        }
    }
}
