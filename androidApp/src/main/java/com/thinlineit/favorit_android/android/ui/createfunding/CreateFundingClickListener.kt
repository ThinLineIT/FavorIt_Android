package com.thinlineit.favorit_android.android.ui.createfunding

import android.content.Context
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.ui.dialog.BottomUpDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateFundingClickListener(
    private val context: Context,
    private val viewModel: CreateFundingViewModel
) {
    private var curDialog: BottomUpDialog? = null

    fun onCreateTopic() {
        curDialog = BottomUpDialog(context).apply {
            bodyText = context.resources.getString(R.string.label_check_fill_content)
            bodySubText = context.resources.getString(R.string.label_check_create_funding)
            confirmButtonText = context.resources.getString(R.string.button_confirm_create_funding)
            dismissButtonText = context.resources.getString(R.string.button_modify_funding)
            confirmClickListener = ::onCreateConfirmClick
            dismissClickListener = ::onCreateDismissClick
        }
        curDialog?.show()
    }

    private fun onCreateConfirmClick() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.createFunding()
            curDialog?.dismiss()
        }
    }

    private fun onCreateDismissClick() {
        curDialog?.dismiss()
    }

}