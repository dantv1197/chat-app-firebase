package com.fg.chat.messenger.domain.usecase.auth

import com.fg.chat.messenger.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }
}
