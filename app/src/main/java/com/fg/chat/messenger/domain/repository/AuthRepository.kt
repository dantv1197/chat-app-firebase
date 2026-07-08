package com.fg.chat.messenger.domain.repository

import android.app.Activity
import com.fg.chat.messenger.domain.model.User
import com.google.firebase.auth.PhoneAuthOptions
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun signUp(username: String, email: String, password: String): Result<User>
    suspend fun logout()
    suspend fun updateStatus(newStatus: String)
    suspend fun updateFcmToken(token: String)
    
    // Phone Authentication
    suspend fun verifyPhoneNumber(phoneNumber: String, activity: Activity): Result<String>
    suspend fun signInWithPhone(verificationId: String, code: String): Result<User>
}
