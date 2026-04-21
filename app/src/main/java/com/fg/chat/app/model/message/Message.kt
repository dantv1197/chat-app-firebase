package com.fg.chat.app.model.message

data class Message(
    val id: String,
    val name: String,
    val lastMessage: String,
    val time: String,
    val profilePic: Int,
    val isOnline: Boolean = false,
    val unreadCount: Int = 0
)
