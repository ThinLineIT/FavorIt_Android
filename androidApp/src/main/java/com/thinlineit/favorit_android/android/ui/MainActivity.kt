package com.thinlineit.favorit_android.android.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.data.entity.FundingState
import com.thinlineit.favorit_android.android.data.entity.Present
import com.thinlineit.favorit_android.android.data.entity.Product
import com.thinlineit.favorit_android.android.databinding.ActivityMainBinding
import com.thinlineit.favorit_android.android.ui.createfunding.CreateFundingActivity
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.ui.fundingList.FundingListActivity
import com.thinlineit.favorit_android.android.ui.present.PresentActivity
import com.thinlineit.favorit_android.android.ui.present.list.PresentListActivity
import com.thinlineit.favorit_android.android.ui.settlefunding.CelebrateFundingFinishActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fundingId = intent.getIntExtra(
            FundingDetailActivity.FUNDING_ID,
            FundingDetailActivity.INVALID_FUNDING_ID
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this@MainActivity
        initButtonClickListener()
        goFundingDetailIfValid(fundingId)
    }

    private fun goFundingDetailIfValid(fundingId: Int) {
        if (fundingId != FundingDetailActivity.INVALID_FUNDING_ID) {
            FundingDetailActivity.start(this, fundingId)
        }
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
            goToPresentListButton.setOnClickListener {
                PresentListActivity.start(this@MainActivity, 95)
            }
            goToFundingSettleButton.setOnClickListener {
                val funding = Funding(
                    "홍민기",
                    "아이패드 사줘요",
                    FundingState.COMPLETED,
                    true,
                    "2022.10.10",
                    "2022.10.25",
                    70,
                    "naver.com",
                    Product(
                        "naver.com",
                        540000,
                    ),
                    "Ipad.jpg"
                )
                val presentList = ArrayList<Present>()
                presentList.add(
                    Present(
                        "홍민기",
                        "윤권",
                        "이거나 먹어",
                        "photo1.jpg",
                        300000
                    )
                )
                presentList.add(
                    Present(
                        "홍민기",
                        "정빈",
                        "이거나 먹어",
                        "photo2.jpg",
                        100000
                    )
                )
                presentList.add(
                    Present(
                        "홍민기",
                        "정민",
                        "이거나 먹어",
                        "photo3.jpg",
                        120000
                    )
                )
                presentList.add(
                    Present(
                        "홍민기",
                        "동기",
                        "이거나 먹어",
                        "photo4.jpg",
                        260000
                    )
                )

                presentList.add(
                    Present(
                        "홍민기",
                        "윤권",
                        "이거나 먹어",
                        "photo1.jpg",
                        300000
                    )
                )
                presentList.add(
                    Present(
                        "홍민기",
                        "정빈",
                        "이거나 먹어",
                        "photo2.jpg",
                        100000
                    )
                )
                presentList.add(
                    Present(
                        "홍민기",
                        "정민",
                        "이거나 먹어",
                        "photo3.jpg",
                        120000
                    )
                )
                presentList.add(
                    Present(
                        "홍민기",
                        "동기",
                        "이거나 먹어",
                        "photo4.jpg",
                        260000
                    )
                )
                CelebrateFundingFinishActivity.start(this@MainActivity, funding, presentList)
            }
        }
    }

    companion object {
        fun start(context: Context) {
            start(context, FundingDetailActivity.INVALID_FUNDING_ID)
        }

        fun start(context: Context, fundingId: Int) {
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra(FundingDetailActivity.FUNDING_ID, fundingId)
            }
            context.startActivity(intent)
        }
    }
}
