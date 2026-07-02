package com.fg.chat.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fg.chat.messenger.domain.model.Chat
import com.fg.chat.messenger.domain.usecase.chat.GetChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatListState())
    val state: StateFlow<ChatListState> = _state.asStateFlow()

    init {
        handleIntent(ChatListIntent.LoadChats)
    }

    fun handleIntent(intent: ChatListIntent) {
        when (intent) {
            is ChatListIntent.LoadChats -> loadChats()
            is ChatListIntent.Search -> {
                _state.value = _state.value.copy(searchQuery = intent.query)
            }
        }
    }

    private fun loadChats() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getChatsUseCase().collectLatest { chats ->
                _state.value = _state.value.copy(chats = chats, isLoading = false)
            }
        }
    }
}

data class ChatListState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)

sealed class ChatListIntent {
    object LoadChats : ChatListIntent()
    data class Search(val query: String) : ChatListIntent()
}
