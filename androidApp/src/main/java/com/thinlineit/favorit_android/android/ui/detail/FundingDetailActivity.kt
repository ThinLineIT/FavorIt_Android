package com.thinlineit.favorit_android.android.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.util.shortToast

class FundingDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funding_detail)
        val fundingID = intent.getStringExtra(FUNDING_ID)
        shortToast("fundingID is $fundingID")
    }

    companion object {
        fun start(context: Context, fundingId: String) {
            val intent = Intent(context, FundingDetailActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
            }
            context.startActivity(intent)
        }

        private const val FUNDING_ID = "FUNDING_ID"
    }
}
