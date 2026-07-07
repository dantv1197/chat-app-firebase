package com.fg.chat.app.model.user

import com.fg.chat.app.model.status.Status
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Model representing a User in the E-Chat app.
 */
@IgnoreExtraProperties
data class User(
    val uid: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val status: Status.User? = Status.User.AVAILABLE,
    val phoneNumber: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0L,
    val fcmToken: String? = null
) {
    constructor() : this("", "", "", null, null, null, false, 0L, null)
}
