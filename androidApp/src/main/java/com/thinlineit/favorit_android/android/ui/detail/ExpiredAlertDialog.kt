package com.thinlineit.favorit_android.android.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.thinlineit.favorit_android.android.R

class ExpiredAlertDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_alert_funding_expired, container, false)
        view.findViewById<Button>(R.id.confirmButton).setOnClickListener {
            dialog?.dismiss()
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.TransparentDialog)
    }
}
