package com.tejas.relifemedicalsystemtest.data

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesSessionManager(context: Context) : SessionManager {

    companion object {
        private const val SESSION_MANAGER = "sessionManager"
        private const val KEY_IS_LOGGED_IN = "sp_is_logged_in"
    }

    private val sharedPreferences =
        context.getSharedPreferences(SESSION_MANAGER, Context.MODE_PRIVATE)

    override var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, value)
        }

    override fun login(isLoggedIn: Boolean) {
        this.isLoggedIn = isLoggedIn
    }

    override fun logout() {
        sharedPreferences.edit {
            clear()
        }
    }
}