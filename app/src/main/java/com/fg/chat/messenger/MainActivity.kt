package com.fg.chat.messenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessengerTheme {
                MessengerApp()
            }
        }
    }
}

@Composable
fun MessengerApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
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
