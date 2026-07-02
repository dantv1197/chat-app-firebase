package com.fg.chat.messenger.domain.model

import java.util.UUID

data class Chat(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isGroup: Boolean = false,
    val lastMessage: String? = null,
    val lastMessageTime: Long? = null,
    val avatarUrl: String? = null,
    val members: List<String> = emptyList()
)
