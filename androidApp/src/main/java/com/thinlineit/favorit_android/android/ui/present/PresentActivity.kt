package com.thinlineit.favorit_android.android.ui.present

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.util.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PresentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_present)
        val fundingId =
            intent.getIntExtra(FUNDING_ID, INVALID_FUNDING_ID).takeIf { it != INVALID_FUNDING_ID }
                ?: throw Exception("Funding ID is invalid")
        shortToast("$FUNDING_ID is $fundingId")
    }

    companion object {
        fun start(context: Context, fundingId: Int, fundingTitle: String) {
            val intent = Intent(context, PresentActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
                putExtra(FUNDING_TITLE, fundingTitle)
            }
            context.startActivity(intent)
        }

        const val FUNDING_ID = "FUNDING_ID"
        const val FUNDING_TITLE = "FUNDING_TITLE"
        private const val INVALID_FUNDING_ID = -1
    }
}
