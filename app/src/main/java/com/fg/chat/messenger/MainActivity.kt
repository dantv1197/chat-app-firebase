package com.fg.chat.messenger

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fg.chat.messenger.ui.ChatDetailScreen
import com.fg.chat.messenger.ui.ChatListScreen
import com.fg.chat.messenger.ui.LoginScreen
import com.fg.chat.messenger.ui.ProfileScreen
import com.fg.chat.messenger.ui.SignUpScreen
import com.fg.chat.messenger.ui.theme.MessengerTheme
import com.fg.chat.messenger.viewmodel.AuthState
import com.fg.chat.messenger.viewmodel.MainViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("MainActivity", "Notification permission granted")
        } else {
            Log.d("MainActivity", "Notification permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        askNotificationPermission()
        
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("MainActivity", "FCM Token: $token")
            viewModel.updateFcmToken(token)
        }

        setContent {
            MessengerTheme {
                MessengerApp(viewModel)
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Permission already granted
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Composable
fun MessengerApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val authState by viewModel.authState.collectAsState()

    when (authState) {
        is AuthState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> {
            val startDestination = if (authState is AuthState.Authenticated) "chat_list" else "login"
            
            NavHost(navController = navController, startDestination = startDestination) {
                composable("login") {
                    LoginScreen(
                        viewModel = hiltViewModel(),
                        onLoginSuccess = {
                            navController.navigate("chat_list") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onSignUpClick = {
                            navController.navigate("signup")
                        }
                    )
                }
                composable("signup") {
                    SignUpScreen(
                        viewModel = hiltViewModel(),
                        onSignUpSuccess = {
                            navController.navigate("chat_list") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("chat_list") {
                    ChatListScreen(
                        viewModel = hiltViewModel(),
                        onChatClick = { chatId ->
                            navController.navigate("chat_detail/$chatId")
                        },
                        onAddChatClick = {
                            // Navigate to contact selection or new group screen
                        },
                        onProfileClick = {
                            navController.navigate("profile")
                        }
                    )
                }
                composable("chat_detail/{chatId}") { backStackEntry ->
                    val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
                    ChatDetailScreen(
                        viewModel = hiltViewModel(),
                        chatId = chatId,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
                composable("profile") {
                    ProfileScreen(
                        viewModel = hiltViewModel(),
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
