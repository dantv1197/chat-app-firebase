package com.fg.chat.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.usecase.auth.LogoutUseCase
import com.fg.chat.messenger.domain.usecase.profile.GetUserProfileUseCase
import com.fg.chat.messenger.domain.usecase.profile.UpdateStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateStatusUseCase: UpdateStatusUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        handleIntent(ProfileIntent.LoadProfile)
    }

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadProfile()
            is ProfileIntent.ToggleEditStatus -> {
                val isEditing = !_state.value.isEditingStatus
                _state.value = _state.value.copy(
                    isEditingStatus = isEditing,
                    newStatusMessage = _state.value.user?.statusMessage ?: ""
                )
            }
            is ProfileIntent.StatusMessageChanged -> {
                _state.value = _state.value.copy(newStatusMessage = intent.newStatus)
            }
            is ProfileIntent.SaveStatus -> saveStatus()
            is ProfileIntent.Logout -> logout()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getUserProfileUseCase().collectLatest { user ->
                _state.value = _state.value.copy(user = user, isLoading = false)
            }
        }
    }

    private fun saveStatus() {
        viewModelScope.launch {
            updateStatusUseCase(_state.value.newStatusMessage)
            _state.value = _state.value.copy(isEditingStatus = false)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _state.value = _state.value.copy(isLoggedOut = true)
        }
    }
}

data class ProfileState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val isEditingStatus: Boolean = false,
    val newStatusMessage: String = "",
    val isLoggedOut: Boolean = false
)

sealed class ProfileIntent {
    object LoadProfile : ProfileIntent()
    object ToggleEditStatus : ProfileIntent()
    data class StatusMessageChanged(val newStatus: String) : ProfileIntent()
    object SaveStatus : ProfileIntent()
    object Logout : ProfileIntent()
}
