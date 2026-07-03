package com.fg.chat.messenger.data.local

import android.content.Context
import android.content.SharedPreferences
import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.model.UserStatus
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PreferenceManager @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("messenger_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_STATUS = "status"
        private const val KEY_STATUS_MESSAGE = "status_message"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUser(user: User) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_ID, user.id)
            putString(KEY_USERNAME, user.username)
            putString(KEY_EMAIL, user.email)
            putString(KEY_STATUS, user.status.name)
            putString(KEY_STATUS_MESSAGE, user.statusMessage)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun getUser(): User? {
        val isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        if (!isLoggedIn) return null

        val id = sharedPreferences.getString(KEY_USER_ID, "") ?: ""
        val username = sharedPreferences.getString(KEY_USERNAME, "") ?: ""
        val email = sharedPreferences.getString(KEY_EMAIL, "") ?: ""
        val statusName = sharedPreferences.getString(KEY_STATUS, UserStatus.OFFLINE.name)
        val statusMessage = sharedPreferences.getString(KEY_STATUS_MESSAGE, null)

        return User(
            id = id,
            username = username,
            email = email,
            status = try { UserStatus.valueOf(statusName!!) } catch (e: Exception) { UserStatus.OFFLINE },
            statusMessage = statusMessage
        )
    }

    fun clearUser() {
        sharedPreferences.edit { clear() }
    }
}
