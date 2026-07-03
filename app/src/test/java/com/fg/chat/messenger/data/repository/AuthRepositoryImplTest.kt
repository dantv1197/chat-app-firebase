package com.fg.chat.messenger.data.repository

import com.fg.chat.messenger.data.local.PreferenceManager
import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.model.UserStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AuthRepositoryImplTest {

    private val preferenceManager = mockk<PreferenceManager>(relaxed = true)
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setup() {
        // Default behavior: no user saved
        every { preferenceManager.getUser() } returns null
        repository = AuthRepositoryImpl(preferenceManager)
    }

    @Test
    fun `when repository is initialized, it loads user from preferences`() = runTest {
        val savedUser = User(username = "saved", email = "saved@test.com", status = UserStatus.ONLINE)
        every { preferenceManager.getUser() } returns savedUser
        
        val newRepo = AuthRepositoryImpl(preferenceManager)
        
        val currentUser = newRepo.getCurrentUser().first()
        assertEquals(savedUser, currentUser)
    }

    @Test
    fun `when login successful, user is saved to preferences`() = runTest {
        val email = "test@example.com"
        val password = "password"

        val result = repository.login(email, password)

        val user = result.getOrNull()
        assertEquals(email, user?.email)
        verify { preferenceManager.saveUser(any()) }
    }

    @Test
    fun `when logout, user is cleared from preferences`() = runTest {
        repository.logout()

        assertNull(repository.getCurrentUser().first())
        verify { preferenceManager.clearUser() }
    }

    @Test
    fun `when update status, updated user is saved to preferences`() = runTest {
        val email = "test@example.com"
        repository.login(email, "password")
        
        val newStatus = "Feeling good"
        repository.updateStatus(newStatus)

        val user = repository.getCurrentUser().first()
        assertEquals(newStatus, user?.statusMessage)
        verify(exactly = 2) { preferenceManager.saveUser(any()) } // Once for login, once for status update
    }
}
