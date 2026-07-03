package com.fg.chat.messenger.data.repository

import com.fg.chat.messenger.domain.model.Chat
import com.fg.chat.messenger.domain.model.Message
import com.fg.chat.messenger.domain.model.MessageStatus
import com.fg.chat.messenger.domain.repository.ChatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class ChatRepositoryImpl : ChatRepository {
    
    private val messages = MutableStateFlow<List<Message>>(emptyList())

    override fun getChats(): Flow<List<Chat>> = flow {
        delay(1000)
        emit(listOf(
            Chat(id = "1", name = "Alice Smith", lastMessage = "See you tomorrow!", lastMessageTime = System.currentTimeMillis() - 3600000),
            Chat(id = "2", name = "Android Dev Group", isGroup = true, lastMessage = "John: The build is ready.", lastMessageTime = System.currentTimeMillis() - 7200000),
            Chat(id = "3", name = "Bob Jones", lastMessage = "Did you check the PR?", lastMessageTime = System.currentTimeMillis() - 86400000)
        ))
    }

    override fun getMessages(chatId: String): Flow<List<Message>> = flow {
        delay(500)
        val initialMessages = listOf(
            Message(senderId = "other", content = "Hey! How are you doing?", chatId = chatId, status = MessageStatus.READ, timestamp = System.currentTimeMillis() - 7200000),
            Message(senderId = "me", content = "I'm good, thanks! Just working on the new app.", chatId = chatId, status = MessageStatus.READ, timestamp = System.currentTimeMillis() - 7100000),
            Message(senderId = "other", content = "That sounds great! Is it the messenger app?", chatId = chatId, status = MessageStatus.READ, timestamp = System.currentTimeMillis() - 7000000),
            Message(senderId = "me", content = "Yes, exactly!", chatId = chatId, status = MessageStatus.DELIVERED, timestamp = System.currentTimeMillis() - 3600000)
        )
        emit(initialMessages)
    }

    override suspend fun sendMessage(message: Message) {
        // Logic to send message to remote/local data source
        delay(200)
    }
}
