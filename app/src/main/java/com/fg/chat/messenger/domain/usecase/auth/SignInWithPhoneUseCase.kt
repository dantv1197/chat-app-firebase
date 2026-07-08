package com.fg.chat.messenger.domain.usecase.auth

import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithPhoneUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(verificationId: String, code: String): Result<User> {
        return repository.signInWithPhone(verificationId, code)
    }
}
