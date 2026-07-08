package com.fg.chat.messenger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.fg.chat.messenger.R
import com.fg.chat.messenger.viewmodel.LoginIntent
import com.fg.chat.messenger.viewmodel.LoginMode
import com.fg.chat.messenger.viewmodel.LoginViewModel
import android.app.Activity

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLoginSuccess()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.welcome_message),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.login_continue),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            if (state.loginMode == LoginMode.EMAIL) {
                EmailLoginFields(state, viewModel)
            } else {
                PhoneLoginFields(state, viewModel, context as Activity)
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (state.loginMode == LoginMode.EMAIL) {
                Button(
                    onClick = { viewModel.handleIntent(LoginIntent.LoginClicked) },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    enabled = !state.isLoading,
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(stringResource(R.string.login))
                    }
                }
            } else {
                if (!state.codeSent) {
                    Button(
                        onClick = { viewModel.handleIntent(LoginIntent.SendCodeClicked(context as Activity)) },
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        enabled = !state.isLoading,
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(stringResource(R.string.send_code))
                        }
                    }
                } else {
                    Button(
                        onClick = { viewModel.handleIntent(LoginIntent.VerifyCodeClicked) },
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        enabled = !state.isLoading,
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(stringResource(R.string.verify_code))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { viewModel.handleIntent(LoginIntent.ToggleLoginMode) }
            ) {
                Text(
                    if (state.loginMode == LoginMode.EMAIL) 
                        stringResource(R.string.use_phone) 
                    else 
                        stringResource(R.string.use_email)
                )
            }

            TextButton(
                onClick = onSignUpClick
            ) {
                Text(stringResource(R.string.dont_have_account))
            }
        }
    }
}

@Composable
fun EmailLoginFields(state: com.fg.chat.messenger.viewmodel.LoginState, viewModel: LoginViewModel) {
    OutlinedTextField(
        value = state.email,
        onValueChange = { viewModel.handleIntent(LoginIntent.EmailChanged(it)) },
        label = { Text(stringResource(R.string.email)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        isError = state.error != null
    )
    
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = state.password,
        onValueChange = { viewModel.handleIntent(LoginIntent.PasswordChanged(it)) },
        label = { Text(stringResource(R.string.password)) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        isError = state.error != null
    )
}

@Composable
fun PhoneLoginFields(
    state: com.fg.chat.messenger.viewmodel.LoginState, 
    viewModel: LoginViewModel,
    activity: Activity
) {
    OutlinedTextField(
        value = state.phoneNumber,
        onValueChange = { viewModel.handleIntent(LoginIntent.PhoneChanged(it)) },
        label = { Text(stringResource(R.string.phone_number)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        isError = state.error != null,
        enabled = !state.codeSent
    )
    
    if (state.codeSent) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.verificationCode,
            onValueChange = { viewModel.handleIntent(LoginIntent.CodeChanged(it)) },
            label = { Text(stringResource(R.string.verification_code)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = state.error != null
        )
    }
}
