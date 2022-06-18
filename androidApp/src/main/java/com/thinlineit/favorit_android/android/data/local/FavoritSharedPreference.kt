package com.thinlineit.favorit_android.android.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object FavoritSharedPreference {
    lateinit var preferences: SharedPreferences
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    fun setAccessToken(value: String) {
        preferences.edit {
            putString(ACCESS_TOKEN, value)
            apply()
        }
    }

    fun getAccessToken() = preferences.getString(ACCESS_TOKEN, "")
}