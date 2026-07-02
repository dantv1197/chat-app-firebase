package com.fg.chat.messenger.domain.usecase.profile

import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetUserProfileUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Flow<User?> {
        return repository.getCurrentUser()
    }
}
