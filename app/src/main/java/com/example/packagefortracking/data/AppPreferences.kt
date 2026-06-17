package com.example.packagefortracking.data

import android.content.Context

class AppPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(
        "package_tracker_preferences",
        Context.MODE_PRIVATE
    )

    fun isNotificationsEnabled(): Boolean {
        return prefs.getBoolean("notifications_enabled", true)
    }

    fun setNotificationsEnabled(value: Boolean) {
        prefs.edit().putBoolean("notifications_enabled", value).apply()
    }

    fun isAutoRefreshEnabled(): Boolean {
        return prefs.getBoolean("auto_refresh_enabled", true)
    }

    fun setAutoRefreshEnabled(value: Boolean) {
        prefs.edit().putBoolean("auto_refresh_enabled", value).apply()
    }

    fun isDeliveryAlertsEnabled(): Boolean {
        return prefs.getBoolean("delivery_alerts_enabled", true)
    }

    fun setDeliveryAlertsEnabled(value: Boolean) {
        prefs.edit().putBoolean("delivery_alerts_enabled", value).apply()
    }

    fun isDarkModeEnabled(): Boolean {
        return prefs.getBoolean("dark_mode_enabled", false)
    }

    fun setDarkModeEnabled(value: Boolean) {
        prefs.edit().putBoolean("dark_mode_enabled", value).apply()
    }
}