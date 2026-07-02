package com.fg.chat.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fg.chat.messenger.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun handleIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UsernameChanged -> _state.value = _state.value.copy(username = intent.username, error = null)
            is SignUpIntent.EmailChanged -> _state.value = _state.value.copy(email = intent.email, error = null)
            is SignUpIntent.PasswordChanged -> _state.value = _state.value.copy(password = intent.password, error = null)
            is SignUpIntent.ConfirmPasswordChanged -> _state.value = _state.value.copy(confirmPassword = intent.confirmPassword, error = null)
            is SignUpIntent.SignUpClicked -> signUp()
        }
    }

    private fun signUp() {
        val s = _state.value
        if (s.username.isBlank() || s.email.isBlank() || s.password.isBlank()) {
            _state.value = s.copy(error = "All fields are required")
            return
        }
        if (s.password != s.confirmPassword) {
            _state.value = s.copy(error = "Passwords do not match")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = signUpUseCase(s.username, s.email, s.password)
            if (result.isSuccess) {
                _state.value = _state.value.copy(isLoading = false, isSuccess = true)
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Sign up failed"
                )
            }
        }
    }
}

data class SignUpState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed class SignUpIntent {
    data class UsernameChanged(val username: String) : SignUpIntent()
    data class EmailChanged(val email: String) : SignUpIntent()
    data class PasswordChanged(val password: String) : SignUpIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent()
    object SignUpClicked : SignUpIntent()
}
