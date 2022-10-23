package com.thinlineit.favorit_android.android.util

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun MutableLiveData<String>.observeToastMessage(context: Context, viewLifecycleOwner: LifecycleOwner) {
    observe(viewLifecycleOwner) { message ->
        if (message.isEmpty()) return@observe
        context.longToast(message)
    }
}
