package com.fg.chat.app.model.message

import com.fg.chat.app.model.user.User

data class Message(
    val messageId: String = "",
    val senderId: User = User(),
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val type: MessageType = MessageType.TEXT, // text, image, file
    val read: Boolean = false
)
