package com.fg.chat.messenger.domain.repository

import com.fg.chat.messenger.domain.model.Chat
import com.fg.chat.messenger.domain.model.Message
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    fun getMessages(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(message: Message)
}
