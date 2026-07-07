package com.fg.chat.app.model.message

import com.fg.chat.app.model.status.Status
import com.fg.chat.app.model.user.User

/**
 * Model representing an item in the main Chats list.
 */
data class ChatListItem(
    val id: String,
    val contact: User? = null,
    val name: String,
    val lastMessage: String,
    val lastSender: User? = null,
    val time: String,
    val profilePic: Int,
    val isGroup: Boolean = false,
    val isOnline: Boolean = false,
    val unreadCount: Int = 0,
    val lastMessageStatus: Status.Message = Status.Message.SENT,
    val isTyping: Boolean = false,
    val groupMembers: List<User>? = null
)
