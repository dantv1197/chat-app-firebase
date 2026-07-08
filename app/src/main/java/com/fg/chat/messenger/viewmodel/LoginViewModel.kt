package com.fg.chat.messenger.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fg.chat.messenger.domain.usecase.auth.LoginUseCase
import com.fg.chat.messenger.domain.usecase.auth.SignInWithPhoneUseCase
import com.fg.chat.messenger.domain.usecase.auth.VerifyPhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val verifyPhoneUseCase: VerifyPhoneUseCase,
    private val signInWithPhoneUseCase: SignInWithPhoneUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email, error = null)
            }
            is LoginIntent.PasswordChanged -> {
                _state.value = _state.value.copy(password = intent.password, error = null)
            }
            is LoginIntent.PhoneChanged -> {
                _state.value = _state.value.copy(phoneNumber = intent.phone, error = null)
            }
            is LoginIntent.CodeChanged -> {
                _state.value = _state.value.copy(verificationCode = intent.code, error = null)
            }
            is LoginIntent.ToggleLoginMode -> {
                _state.value = _state.value.copy(
                    loginMode = if (_state.value.loginMode == LoginMode.EMAIL) LoginMode.PHONE else LoginMode.EMAIL,
                    error = null
                )
            }
            is LoginIntent.LoginClicked -> login()
            is LoginIntent.SendCodeClicked -> sendVerificationCode(intent.activity)
            is LoginIntent.VerifyCodeClicked -> verifyCode()
        }
    }

    private fun login() {
        val currentState = _state.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _state.value = currentState.copy(error = "Please fill all fields")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = loginUseCase(currentState.email, currentState.password)
            
            if (result.isSuccess) {
                _state.value = _state.value.copy(isLoading = false, isSuccess = true)
            } else {
                _state.value = _state.value.copy(
                    isLoading = false, 
                    error = result.exceptionOrNull()?.message ?: "Login failed"
                )
            }
        }
    }

    private fun sendVerificationCode(activity: Activity) {
        val phoneNumber = _state.value.phoneNumber
        if (phoneNumber.isBlank()) {
            _state.value = _state.value.copy(error = "Please enter phone number")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = verifyPhoneUseCase(phoneNumber, activity)
            
            if (result.isSuccess) {
                _state.value = _state.value.copy(
                    isLoading = false, 
                    verificationId = result.getOrNull(),
                    codeSent = true
                )
            } else {
                _state.value = _state.value.copy(
                    isLoading = false, 
                    error = result.exceptionOrNull()?.message ?: "Failed to send code"
                )
            }
        }
    }

    private fun verifyCode() {
        val currentState = _state.value
        val verificationId = currentState.verificationId
        val code = currentState.verificationCode

        if (verificationId == null || code.isBlank()) {
            _state.value = currentState.copy(error = "Please enter verification code")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = signInWithPhoneUseCase(verificationId, code)
            
            if (result.isSuccess) {
                _state.value = _state.value.copy(isLoading = false, isSuccess = true)
            } else {
                _state.value = _state.value.copy(
                    isLoading = false, 
                    error = result.exceptionOrNull()?.message ?: "Verification failed"
                )
            }
        }
    }
}

enum class LoginMode {
    EMAIL, PHONE
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val verificationCode: String = "",
    val verificationId: String? = null,
    val codeSent: Boolean = false,
    val loginMode: LoginMode = LoginMode.EMAIL,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data class PhoneChanged(val phone: String) : LoginIntent()
    data class CodeChanged(val code: String) : LoginIntent()
    object ToggleLoginMode : LoginIntent()
    object LoginClicked : LoginIntent()
    data class SendCodeClicked(val activity: Activity) : LoginIntent()
    object VerifyCodeClicked : LoginIntent()
}
