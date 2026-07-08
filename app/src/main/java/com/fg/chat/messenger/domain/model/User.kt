package com.fg.chat.messenger.domain.model

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val status: UserStatus = UserStatus.OFFLINE,
    val statusMessage: String? = null,
    val fcmToken: String? = null
)
