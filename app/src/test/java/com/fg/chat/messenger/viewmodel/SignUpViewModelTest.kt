package com.fg.chat.messenger.viewmodel

import app.cash.turbine.test
import com.fg.chat.messenger.domain.model.User
import com.fg.chat.messenger.domain.model.UserStatus
import com.fg.chat.messenger.domain.usecase.auth.SignUpUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {

    private val signUpUseCase = mockk<SignUpUseCase>()
    private lateinit var viewModel: SignUpViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SignUpViewModel(signUpUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        val state = viewModel.state.value
        assertEquals("", state.username)
        assertEquals("", state.email)
        assertEquals("", state.password)
        assertEquals("", state.confirmPassword)
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertFalse(state.isSuccess)
    }

    @Test
    fun `when fields change, state is updated`() = runTest {
        viewModel.handleIntent(SignUpIntent.UsernameChanged("user"))
        viewModel.handleIntent(SignUpIntent.EmailChanged("email@test.com"))
        viewModel.handleIntent(SignUpIntent.PasswordChanged("pass"))
        viewModel.handleIntent(SignUpIntent.ConfirmPasswordChanged("pass"))

        val state = viewModel.state.value
        assertEquals("user", state.username)
        assertEquals("email@test.com", state.email)
        assertEquals("pass", state.password)
        assertEquals("pass", state.confirmPassword)
    }

    @Test
    fun `when passwords do not match, error is shown`() = runTest {
        viewModel.handleIntent(SignUpIntent.UsernameChanged("user"))
        viewModel.handleIntent(SignUpIntent.EmailChanged("email@test.com"))
        viewModel.handleIntent(SignUpIntent.PasswordChanged("pass1"))
        viewModel.handleIntent(SignUpIntent.ConfirmPasswordChanged("pass2"))
        
        viewModel.handleIntent(SignUpIntent.SignUpClicked)
        
        assertEquals("Passwords do not match", viewModel.state.value.error)
    }

    @Test
    fun `when sign up successful, isSuccess is true`() = runTest {
        val user = User(username = "user", email = "test@test.com", status = UserStatus.ONLINE)
        coEvery { signUpUseCase("user", "test@test.com", "pass") } returns Result.success(user)

        viewModel.handleIntent(SignUpIntent.UsernameChanged("user"))
        viewModel.handleIntent(SignUpIntent.EmailChanged("test@test.com"))
        viewModel.handleIntent(SignUpIntent.PasswordChanged("pass"))
        viewModel.handleIntent(SignUpIntent.ConfirmPasswordChanged("pass"))

        viewModel.state.test {
            awaitItem() // Skip initial state updates
            
            viewModel.handleIntent(SignUpIntent.SignUpClicked)

            assertTrue(awaitItem().isLoading)

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertTrue(successState.isSuccess)
        }
    }
}
