package com.fg.chat.app.model.message.conversation

data class Message(
    val id: String,
    val text: String,
    val time: String,
    val isFromMe: Boolean
)
