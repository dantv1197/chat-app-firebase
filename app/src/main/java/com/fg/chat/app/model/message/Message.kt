package com.fg.chat.app.model.message

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Model representing an individual Message.
 */
@IgnoreExtraProperties
data class Message(
    val messageId: String = "",
    val senderId: String = "",
    val text: String? = null,
    val mediaUrl: String? = null,
    val mediaType: String? = null,
    val type: MessageType = MessageType.TEXT,
    val timestamp: Long = 0L,
    val seenBy: Map<String, Boolean> = emptyMap()
) {
    constructor() : this("", "", null, null, null, MessageType.TEXT, 0L, emptyMap())
}
