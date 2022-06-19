package com.thinlineit.favorit_android.android.data.local

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritSharedPreference @Inject constructor(@ApplicationContext context: Context) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setAccessToken(value: String) {
        preferences.edit {
            putString(ACCESS_TOKEN, value)
            apply()
        }
    }

    fun getAccessToken() = preferences.getString(ACCESS_TOKEN, DEFAULT_STRING_VALUE)

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val DEFAULT_STRING_VALUE = ""
    }
}