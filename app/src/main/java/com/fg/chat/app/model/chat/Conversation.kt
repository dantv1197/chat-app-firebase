package com.fg.chat.app.model.chat

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Model representing a Conversation or Chat session.
 */
@IgnoreExtraProperties
data class Conversation(
    val chatId: String = "",
    val participants: List<String> = emptyList(),
    val isGroup: Boolean = false,
    val groupName: String? = null,
    val groupPhotoUrl: String? = null,
    val lastMessage: String = "",
    val lastMessageTimestamp: Long = 0L,
    val lastSenderId: String = "",
    val unreadCounts: Map<String, Int> = emptyMap(),
    val admins: List<String> = emptyList()
) {
    constructor() : this("", emptyList(), false, null, null, "", 0L, "", emptyMap(), emptyList())
}
