package com.fg.chat.messenger.data.repository

import android.app.Activity
import com.fg.chat.messenger.data.local.PreferenceManager
import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.model.UserStatus
import com.fg.chat.messenger.domain.repository.AuthRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val preferenceManager: PreferenceManager
) : AuthRepository {
    private val _currentUser = MutableStateFlow<User?>(preferenceManager.getUser())

    override fun getCurrentUser(): Flow<User?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("User is null")
            
            val user = User(
                id = firebaseUser.uid,
                username = firebaseUser.displayName ?: email.substringBefore("@"),
                email = firebaseUser.email ?: email,
                status = UserStatus.ONLINE,
                statusMessage = "Available"
            )
            
            preferenceManager.saveUser(user)
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(username: String, email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("User is null")
            
            // Note: In a real app, you'd also save this to Firestore/Database
            val user = User(
                id = firebaseUser.uid,
                username = username,
                email = email,
                status = UserStatus.ONLINE,
                statusMessage = "New here!"
            )
            
            preferenceManager.saveUser(user)
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
        preferenceManager.clearUser()
        _currentUser.value = null
    }

    override suspend fun updateStatus(newStatus: String) {
        val user = _currentUser.value ?: return
        val updatedUser = user.copy(statusMessage = newStatus)
        preferenceManager.saveUser(updatedUser)
        _currentUser.value = updatedUser
        // In a real app, update Firestore here as well
    }

    override suspend fun updateFcmToken(token: String) {
        val user = _currentUser.value ?: return
        val updatedUser = user.copy(fcmToken = token)
        preferenceManager.saveUser(updatedUser)
        _currentUser.value = updatedUser
        // In a real app, update Firestore here as well
    }

    override suspend fun verifyPhoneNumber(phoneNumber: String, activity: Activity): Result<String> {
        return suspendCancellableCoroutine { continuation ->
            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // This can happen automatically on some devices
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    if (continuation.isActive) {
                        continuation.resumeWith(Result.success(Result.failure(e)))
                    }
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    if (continuation.isActive) {
                        continuation.resumeWith(Result.success(Result.success(verificationId)))
                    }
                }
            }

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()
            
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    override suspend fun signInWithPhone(verificationId: String, code: String): Result<User> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = authResult.user ?: throw Exception("User is null")
            
            val user = User(
                id = firebaseUser.uid,
                username = firebaseUser.phoneNumber ?: "User",
                email = "", // Phone login might not have email
                status = UserStatus.ONLINE,
                statusMessage = "Available"
            )
            
            preferenceManager.saveUser(user)
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
