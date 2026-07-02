package com.fg.chat.messenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fg.chat.messenger.ui.ChatDetailScreen
import com.fg.chat.messenger.ui.ChatListScreen
import com.fg.chat.messenger.ui.LoginScreen
import com.fg.chat.messenger.ui.ProfileScreen
import com.fg.chat.messenger.ui.theme.MessengerTheme

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
                onLoginSuccess = {
                    navController.navigate("chat_list") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("chat_list") {
            ChatListScreen(
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
                chatId = chatId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("profile") {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
