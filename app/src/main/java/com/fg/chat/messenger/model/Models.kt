package com.fg.chat.messenger.model

import java.util.UUID

enum class MessageStatus {
    SENT, DELIVERED, READ
}

enum class UserStatus {
    ONLINE, AWAY, BUSY, OFFLINE
}

data class User(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val status: UserStatus = UserStatus.OFFLINE,
    val statusMessage: String? = null
)

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val chatId: String,
    val senderId: String,
    val content: String,
    val status: MessageStatus = MessageStatus.SENT,
    val timestamp: Long = System.currentTimeMillis(),
    val isMedia: Boolean = false,
    val mediaUrl: String? = null
)

data class Chat(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isGroup: Boolean = false,
    val lastMessage: String? = null,
    val lastMessageTime: Long? = null,
    val avatarUrl: String? = null,
    val members: List<String> = emptyList()
)
