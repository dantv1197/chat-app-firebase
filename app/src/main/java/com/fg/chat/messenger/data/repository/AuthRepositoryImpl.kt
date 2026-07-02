package com.fg.chat.messenger.data.repository

import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.model.UserStatus
import com.fg.chat.messenger.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepositoryImpl : AuthRepository {
    private val _currentUser = MutableStateFlow<User?>(null)

    override fun getCurrentUser(): Flow<User?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Result<User> {
        delay(1000) // Mock network delay
        val user = User(
            username = email.substringBefore("@"),
            email = email,
            status = UserStatus.ONLINE,
            statusMessage = "Available"
        )
        _currentUser.value = user
        return Result.success(user)
    }

    override suspend fun signUp(username: String, email: String, password: String): Result<User> {
        delay(1000) // Mock network delay
        val user = User(
            username = username,
            email = email,
            status = UserStatus.ONLINE,
            statusMessage = "New here!"
        )
        _currentUser.value = user
        return Result.success(user)
    }

    override suspend fun logout() {
        _currentUser.value = null
    }

    override suspend fun updateStatus(newStatus: String) {
        val user = _currentUser.value ?: return
        _currentUser.value = user.copy(statusMessage = newStatus)
    }
}
