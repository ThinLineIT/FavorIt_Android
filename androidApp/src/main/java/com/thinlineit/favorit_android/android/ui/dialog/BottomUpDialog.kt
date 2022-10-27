package com.thinlineit.favorit_android.android.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thinlineit.favorit_android.android.databinding.DialogBottomUpBinding

class BottomUpDialog(context: Context) : BottomSheetDialog(context) {
    private val binding: DialogBottomUpBinding by lazy {
        DialogBottomUpBinding.inflate(layoutInflater)
    }

    var bodyText: String? = null
    var bodySubText: String? = null
    var confirmButtonText: String? = null
    var dismissButtonText: String? = null
    var confirmClickListener: (() -> Unit)? = null
    var dismissClickListener: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        initView()
    }

    private fun initView() {
        bodyText?.let {
            binding.dialogBodyText.visibility = View.VISIBLE
            binding.dialogBodyText.text = it
        } ?: kotlin.run {
            binding.dialogBodyText.visibility = View.GONE
        }

        bodySubText?.let {
            binding.dialogSubText.visibility = View.VISIBLE
            binding.dialogSubText.hint = it
        } ?: kotlin.run {
            binding.dialogSubText.visibility = View.GONE
        }

        confirmButtonText?.let {
            binding.confirmButton.text = it
        }

        dismissButtonText?.let {
            binding.cancelButton.text = it
        }

        confirmClickListener?.let {
            binding.confirmButton.visibility = View.VISIBLE
            binding.confirmButton.setOnClickListener {
                confirmClickListener?.invoke()
            }
        } ?: kotlin.run {
            binding.confirmButton.visibility = View.GONE
        }

        dismissClickListener?.let {
            binding.cancelButton.visibility = View.VISIBLE
            binding.cancelButton.setOnClickListener {
                dismissClickListener?.invoke()
            }
        } ?: run {
            binding.cancelButton.visibility = View.GONE
        }
    }

}