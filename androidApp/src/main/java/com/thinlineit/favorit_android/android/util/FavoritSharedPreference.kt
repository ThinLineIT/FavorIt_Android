package com.thinlineit.favorit_android.android.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object FavoritSharedPreference {
    lateinit var preferences: SharedPreferences
    private const val TOKEN = "TOKEN"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    fun setAccessToken(value: String) {
        preferences.edit {
            putString(TOKEN, value)
            apply()
        }
    }

    fun getAccessToken() = preferences.getString(TOKEN, "")
}