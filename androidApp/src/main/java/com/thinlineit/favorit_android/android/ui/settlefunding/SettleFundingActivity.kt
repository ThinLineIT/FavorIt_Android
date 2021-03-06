package com.thinlineit.favorit_android.android.ui.settlefunding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettleFundingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settle_funding)
    }

    companion object {
        const val FUNDING_ID = "fundingId"
        const val FUNDING_NAME = "fundingName"

        fun start(context: Context, fundingId: Int, fundingName: String) {
            val intent = Intent(context, SettleFundingActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
                putExtra(FUNDING_NAME, fundingName)
            }
            context.startActivity(intent)
        }
    }
}
