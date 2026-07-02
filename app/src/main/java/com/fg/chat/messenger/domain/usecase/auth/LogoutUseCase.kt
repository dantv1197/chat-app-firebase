package com.fg.chat.messenger.domain.usecase.auth

import com.fg.chat.messenger.domain.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }
}
