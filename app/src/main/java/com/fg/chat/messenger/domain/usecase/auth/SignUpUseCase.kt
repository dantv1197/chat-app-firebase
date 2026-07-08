package com.fg.chat.messenger.domain.usecase.auth

import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<User> {
        return repository.signUp(username, email, password)
    }
}
