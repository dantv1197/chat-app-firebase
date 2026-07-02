package com.fg.chat.messenger.domain.usecase.profile

import com.fg.chat.messenger.domain.repository.AuthRepository

class UpdateStatusUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(newStatus: String) {
        repository.updateStatus(newStatus)
    }
}
