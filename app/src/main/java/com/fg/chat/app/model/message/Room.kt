package com.fg.chat.app.model.message

import com.fg.chat.app.model.user.User

data class Room(
    val roomId: String = "",
    val roomName: String = "",
    val roomImage: String = "",
    val isGroup: Boolean = false,
    val lastMessage: String = "",
    val lastMessageTimestamp: Long = 0L,
    val unreadCount: Int = 0,
    val members: List<User> = emptyList()
)
