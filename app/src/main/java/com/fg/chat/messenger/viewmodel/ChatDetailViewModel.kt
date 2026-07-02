package com.fg.chat.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fg.chat.messenger.domain.model.Message
import com.fg.chat.messenger.domain.model.MessageStatus
import com.fg.chat.messenger.domain.usecase.chat.GetMessagesUseCase
import com.fg.chat.messenger.domain.usecase.chat.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatDetailState())
    val state: StateFlow<ChatDetailState> = _state.asStateFlow()

    fun handleIntent(intent: ChatDetailIntent) {
        when (intent) {
            is ChatDetailIntent.LoadChat -> loadChat(intent.chatId)
            is ChatDetailIntent.MessageTextChanged -> {
                _state.value = _state.value.copy(messageText = intent.text)
            }
            is ChatDetailIntent.SendMessage -> sendMessage()
        }
    }

    private fun loadChat(chatId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(chatId = chatId, isLoading = true)
            getMessagesUseCase(chatId).collectLatest { messages ->
                _state.value = _state.value.copy(messages = messages, isLoading = false)
            }
        }
    }

    private fun sendMessage() {
        val currentState = _state.value
        val text = currentState.messageText
        if (text.isNotBlank()) {
            viewModelScope.launch {
                val newMessage = Message(
                    senderId = "me",
                    content = text,
                    chatId = currentState.chatId,
                    timestamp = System.currentTimeMillis(),
                    status = MessageStatus.SENT
                )
                sendMessageUseCase.invoke(newMessage)
                _state.value = _state.value.copy(messageText = "")
            }
        }
    }
}

data class ChatDetailState(
    val chatId: String = "",
    val chatName: String = "Alice Smith",
    val isOnline: Boolean = true,
    val messageText: String = "",
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)

sealed class ChatDetailIntent {
    data class LoadChat(val chatId: String) : ChatDetailIntent()
    data class MessageTextChanged(val text: String) : ChatDetailIntent()
    object SendMessage : ChatDetailIntent()
}
