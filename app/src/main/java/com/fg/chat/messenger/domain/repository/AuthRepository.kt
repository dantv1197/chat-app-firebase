package com.fg.chat.messenger.domain.repository

import com.fg.chat.messenger.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signUp(username: String, email: String, password: String): Result<User>
    suspend fun logout()
    suspend fun updateStatus(newStatus: String)
}
