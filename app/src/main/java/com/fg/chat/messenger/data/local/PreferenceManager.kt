package com.fg.chat.messenger.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.model.UserStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("messenger_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_AVATAR_URL = "avatar_url"
        private const val KEY_STATUS = "status"
        private const val KEY_STATUS_MESSAGE = "status_message"
        private const val KEY_FCM_TOKEN = "fcm_token"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUser(user: User) {
        sharedPreferences.edit {
            putString(KEY_USER_ID, user.id)
            putString(KEY_USERNAME, user.username)
            putString(KEY_EMAIL, user.email)
            putString(KEY_AVATAR_URL, user.avatarUrl)
            putString(KEY_STATUS, user.status.name)
            putString(KEY_STATUS_MESSAGE, user.statusMessage)
            putString(KEY_FCM_TOKEN, user.fcmToken)
            putBoolean(KEY_IS_LOGGED_IN, true)
        }
    }

    fun getUser(): User? {
        val isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        if (!isLoggedIn) return null

        val id = sharedPreferences.getString(KEY_USER_ID, "") ?: ""
        val username = sharedPreferences.getString(KEY_USERNAME, "") ?: ""
        val email = sharedPreferences.getString(KEY_EMAIL, "") ?: ""
        val avatarUrl = sharedPreferences.getString(KEY_AVATAR_URL, null)
        val statusName = sharedPreferences.getString(KEY_STATUS, UserStatus.OFFLINE.name)
        val statusMessage = sharedPreferences.getString(KEY_STATUS_MESSAGE, null)
        val fcmToken = sharedPreferences.getString(KEY_FCM_TOKEN, null)

        return User(
            id = id,
            username = username,
            email = email,
            avatarUrl = avatarUrl,
            status = try {
                UserStatus.valueOf(statusName!!)
            } catch (e: Exception) {
                UserStatus.OFFLINE
            },
            statusMessage = statusMessage,
            fcmToken = fcmToken
        )
    }

    fun clearUser() {
        sharedPreferences.edit { clear() }
    }
}
