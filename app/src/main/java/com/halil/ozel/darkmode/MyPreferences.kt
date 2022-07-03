package com.halil.ozel.darkmode

import android.content.Context
import androidx.preference.PreferenceManager

class MyPreferences(context: Context) {

    companion object {
        private const val DARK_STATUS = "darkStatus"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_STATUS, 0)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
}