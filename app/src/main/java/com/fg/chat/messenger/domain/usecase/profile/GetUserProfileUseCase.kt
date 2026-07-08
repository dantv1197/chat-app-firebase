package com.fg.chat.messenger.domain.usecase.profile

import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(): Flow<User?> {
        return repository.getCurrentUser()
    }
}
