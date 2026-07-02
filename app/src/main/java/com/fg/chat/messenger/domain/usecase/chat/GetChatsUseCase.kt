package com.fg.chat.messenger.domain.usecase.chat

import com.fg.chat.messenger.domain.model.Chat
import com.fg.chat.messenger.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatsUseCase(private val repository: ChatRepository) {
    operator fun invoke(): Flow<List<Chat>> {
        return repository.getChats()
    }
}
