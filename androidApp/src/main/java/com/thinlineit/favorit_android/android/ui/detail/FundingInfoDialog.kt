package com.thinlineit.favorit_android.android.ui.detail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.thinlineit.favorit_android.android.data.entity.Funding
import com.thinlineit.favorit_android.android.databinding.DialogFundingInfoBinding
import com.thinlineit.favorit_android.android.di.GlideApp
import com.thinlineit.favorit_android.android.ui.present.list.Present

class FundingInfoDialog(
    context: Context,
    val funding: Funding
) : Dialog(context) {

    private val binding: DialogFundingInfoBinding by lazy {
        DialogFundingInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        binding.apply {
            GlideApp.with(context)
                .load(funding.image)
                .centerCrop()
                .into(backgroundImage)
            fundingName.text = funding.name
            linkText.text = funding.product.link
            content.text = funding.description
            price.text = funding.product.price.toString()
            fundingDate.text = funding.expiredDate
        }

    }

}