package com.thinlineit.favorit_android.android.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object FavoritSharedPreference {
    lateinit var preferences: SharedPreferences
    private const val DEFAULT_STRING_VALUE = ""

    fun init(context: Context) {
        preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    fun setValue(key: String, value: String) {
        preferences.edit {
            putString(key, value)
            apply()
        }
    }

    fun getValue(key: String): String? = preferences.getString(key, DEFAULT_STRING_VALUE)
}
