package com.thinlineit.favorit_android.android.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R

class ClosedFundingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_closed_funding)
    }

    companion object {
        private const val FUNDING_ID = "fundingId"
        fun start(context: Context, fundingId: Int) {
            val intent = Intent(context, ClosedFundingActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
            }
            context.startActivity(intent)
        }
    }
}
