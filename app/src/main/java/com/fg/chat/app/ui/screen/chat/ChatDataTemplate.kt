package com.fg.chat.app.ui.screen.chat

import com.fg.chat.app.R
import com.fg.chat.app.model.message.ChatListItem
import com.fg.chat.app.model.message.Message
import com.fg.chat.app.model.user.User
import com.fg.chat.app.model.group.Friend

/**
 * Template for mock data used in Chat screens.
 */
object ChatDataTemplate {
    
    val mockUsers = listOf(
        User(uid = "1", displayName = "Alex Linderson", photoUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=Alex", status = "Available", isOnline = true),
        User(uid = "2", displayName = "Angelina Jolie", photoUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=Angelina", status = "At work", isOnline = false),
        User(uid = "3", displayName = "John Doe", photoUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=John", status = "Busy", isOnline = true),
        User(uid = "4", displayName = "Emma Watson", photoUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=Emma", status = "Available", isOnline = true)
    )

    val mockChatList = listOf(
        ChatListItem(
            id = "chat_1",
            name = "Alex Linderson",
            lastMessage = "How are you today?",
            time = "2:30 PM",
            profilePic = R.drawable.image_introduce_1,
            isOnline = true,
            unreadCount = 1,
            contact = mockUsers[0]
        ),
        ChatListItem(
            id = "chat_2",
            name = "Family Chat",
            lastMessage = "John: See you all tonight!",
            time = "Yesterday",
            profilePic = R.drawable.image_introduce_2,
            isGroup = true,
            unreadCount = 5
        ),
        ChatListItem(
            id = "chat_3",
            name = "John Doe",
            lastMessage = "Thanks for the help",
            time = "11:15 AM",
            profilePic = R.drawable.image_introduce_3,
            isOnline = true,
            unreadCount = 0,
            contact = mockUsers[2]
        )
    )

    val mockFriends = mockUsers.map { 
        Friend(id = it.uid, name = it.displayName, profilePic = "https://api.dicebear.com/7.x/avataaars/svg?seed=${it.displayName}")
    }

    val mockMessages = listOf(
        Message(messageId = "m1", senderId = "1", text = "Hello!", timestamp = 1715856000000),
        Message(messageId = "m2", senderId = "me", text = "Hi Alex, how are you?", timestamp = 1715856060000),
        Message(messageId = "m3", senderId = "1", text = "I'm doing great, thanks for asking!", timestamp = 1715856120000)
    )
}
