package com.thinlineit.favorit_android.android.ui.present

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.entity.PresentResult
import com.thinlineit.favorit_android.android.databinding.ActivityPresentFinishBinding
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity

class PresentFinishActivity : AppCompatActivity() {

    private val binding: ActivityPresentFinishBinding by lazy {
        ActivityPresentFinishBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val presentResult: PresentResult = if (Build.VERSION.SDK_INT >= 33) {
            intent.getSerializableExtra(PRESENT_RESULT, PresentResult::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(PRESENT_RESULT) as? PresentResult
        } ?: run {
            finish()
            return
        }
        binding.presentFinishFundingNameTextView.text =presentResult.fundingName
        binding.presentFinishTextView.text = getString(
            R.string.label_success_to_present,
            presentResult.amount
        )
        binding.goToFundingDetailButton.setOnClickListener {
            FundingDetailActivity.start(this, presentResult.fundingID)
        }
    }

    companion object {
        fun start(context: Context, presentResult: PresentResult) {
            val intent = Intent(context, PresentFinishActivity::class.java).apply {
                putExtra(PRESENT_RESULT, presentResult)
            }
            context.startActivity(intent)
        }

        private const val PRESENT_RESULT = "PRESENT_RESULT"
    }
}
