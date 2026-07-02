package com.fg.chat.messenger.domain.usecase.chat

import com.fg.chat.messenger.domain.model.Message
import com.fg.chat.messenger.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(private val repository: ChatRepository) {
    operator fun invoke(chatId: String): Flow<List<Message>> {
        return repository.getMessages(chatId)
    }
}
