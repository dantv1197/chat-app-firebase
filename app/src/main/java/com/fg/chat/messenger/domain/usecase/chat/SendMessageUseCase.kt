package com.fg.chat.messenger.domain.usecase.chat

import com.fg.chat.messenger.domain.model.Message
import com.fg.chat.messenger.domain.repository.ChatRepository

class SendMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(message: Message) {
        repository.sendMessage(message)
    }
}
