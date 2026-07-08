package com.fg.chat.messenger.domain.usecase.chat

import com.fg.chat.messenger.domain.model.Message
import com.fg.chat.messenger.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val repository: ChatRepository) {
    operator fun invoke(chatId: String): Flow<List<Message>> {
        return repository.getMessages(chatId)
    }
}
