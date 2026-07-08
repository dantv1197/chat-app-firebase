package com.fg.chat.messenger.domain.usecase.auth

import android.app.Activity
import com.fg.chat.messenger.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyPhoneUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(phoneNumber: String, activity: Activity): Result<String> {
        return repository.verifyPhoneNumber(phoneNumber, activity)
    }
}
