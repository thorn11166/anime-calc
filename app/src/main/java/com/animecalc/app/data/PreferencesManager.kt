package com.animecalc.app.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "calculator_prefs",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
    }

    fun isDarkMode(): Boolean {
        return prefs.getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
    }

    fun toggleDarkMode() {
        setDarkMode(!isDarkMode())
    }
}
