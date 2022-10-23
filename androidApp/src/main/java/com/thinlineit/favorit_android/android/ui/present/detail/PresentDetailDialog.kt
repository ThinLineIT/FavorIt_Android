package com.thinlineit.favorit_android.android.ui.present.detail

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.thinlineit.favorit_android.android.databinding.DialogPresentDetailBinding
import com.thinlineit.favorit_android.android.di.GlideApp
import com.thinlineit.favorit_android.android.ui.present.list.Present

class PresentDetailDialog(
    context: Context,
    private val present: Present
) : Dialog(context) {

    private val binding: DialogPresentDetailBinding by lazy {
        DialogPresentDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        binding.apply {
            GlideApp.with(context)
                .load(present.photo)
                .centerCrop()
                .into(photoImageView)
            makerNickNameTextView.text = present.makerNickName
            presentMessageTextView.text = present.message
            supporterNameTextView.text = present.supporterNickName
            presentAmountTextView.text = present.amount.toString()
        }

    }
}