package com.fg.chat.app.model.user

data class User(
    val uid: String = "",
    val displayName: String = "",
    val phoneNumber: String = "",
    val photoUrl: String = "",
    val status: Status = Status.OFFLINE,
    val lastSeen: Long = 0L
)