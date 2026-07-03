package com.fg.chat.messenger.viewmodel

import app.cash.turbine.test
import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.model.UserStatus
import com.fg.chat.messenger.domain.repository.AuthRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val authRepository = mockk<AuthRepository>()
    private val testDispatcher = StandardTestDispatcher()
    private val currentUserFlow = MutableStateFlow<User?>(null)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { authRepository.getCurrentUser() } returns currentUserFlow
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val viewModel = MainViewModel(authRepository)
        assertEquals(AuthState.Loading, viewModel.authState.value)
    }

    @Test
    fun `when user is authenticated, state is Authenticated`() = runTest {
        val user = User(username = "test", email = "test@test.com", status = UserStatus.ONLINE)
        currentUserFlow.value = user
        
        val viewModel = MainViewModel(authRepository)
        
        viewModel.authState.test {
            assertEquals(AuthState.Authenticated, awaitItem())
        }
    }

    @Test
    fun `when user is not authenticated, state is Unauthenticated`() = runTest {
        currentUserFlow.value = null
        
        val viewModel = MainViewModel(authRepository)
        
        viewModel.authState.test {
            assertEquals(AuthState.Unauthenticated, awaitItem())
        }
    }
}
