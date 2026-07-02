package com.fg.chat.messenger.domain.usecase.auth

import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.login(email, password)
    }
}
