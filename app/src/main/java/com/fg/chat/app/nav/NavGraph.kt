package com.fg.chat.app.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fg.chat.app.model.path.NavPath
import com.fg.chat.app.ui.screen.chat.ChatsScreen
import com.fg.chat.app.ui.screen.chat.ConversationScreen
import com.fg.chat.app.ui.screen.chat.CreateGroupScreen
import com.fg.chat.app.ui.screen.login.LoginScreen
import com.fg.chat.app.ui.screen.login.OtpScreen
import com.fg.chat.app.ui.screen.login.RegisterScreen
import com.fg.chat.app.ui.screen.more.HelpCenterScreen
import com.fg.chat.app.ui.screen.more.InviteFriendScreen
import com.fg.chat.app.ui.screen.more.MoreScreen
import com.fg.chat.app.ui.screen.more.OthersScreen
import com.fg.chat.app.ui.screen.more.SecurityScreen
import com.fg.chat.app.ui.screen.onboarding.IntroduceScreen
import com.fg.chat.app.ui.screen.onboarding.OnboardingScreen
import com.fg.chat.app.ui.screen.profile.ProfileScreen
import com.fg.chat.app.ui.screen.messenger.MessengerConversation
import com.fg.chat.app.ui.screen.messenger.MessengerHome
import com.google.gson.Gson

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MessengerHome.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onFinish = {
                navController.navigate("introduce") {
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }
        composable(Screen.Introduce.route) {
            IntroduceScreen(onSkip = {
                navController.navigate("login") {
                    popUpTo("introduce") { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(onNavigateToNext = {}, onNavigateToRegister = {})
        }
        composable(Screen.OtpVerify.route) {
            OtpScreen (onBack = {}, onNext = {})
        }
        composable(Screen.Register.route) {
            RegisterScreen(onNavigateToNext = { key, value -> {} }, onBack = {})
        }
        composable(
            Screen.Conversation.route,
            arguments = listOf(navArgument("user") { type = NavType.StringType })
        ) { entryValue ->
            val navPath =
                Gson().fromJson(entryValue.arguments?.getString("user") ?: "", NavPath::class.java)
            ConversationScreen(
                userName = navPath.userName,
                userProfilePic = navPath.userProfilePic,
                onBackClick = { navController.popBackStack() })
        }
        composable(Screen.Chat.route) {
            ChatsScreen(
                onChatClick = { chatId ->
                    // Navigation logic to Conversation
                },
                onAddClick = {},
                onSearchClick = {},
                onAddFriendClick = {},
                onCreateGroupClick = {
                    navController.navigate(Screen.CreateGroup.route)
                }
            )
        }
        composable(Screen.HelpCenter.route) {
            HelpCenterScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.InviteFriend.route) {
            InviteFriendScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.More.route) {
            MoreScreen(
                onBackClick = { navController.popBackStack() },
                onInviteFriendClick = { navController.navigate(Screen.InviteFriend.route) },
                onSecurityClick = { navController.navigate(Screen.Security.route) },
                onHelpCenterClick = { navController.navigate(Screen.HelpCenter.route) }
            )
        }
        composable(Screen.Other.route) {
            OthersScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Security.route) {
            SecurityScreen(
                onBackClick = { navController.popBackStack() },
                onChangePinClick = {},
                onFingerprintClick = {},
                onFaceIdClick = {}
            )
        }
        composable(Screen.CreateGroup.route) {
            CreateGroupScreen(
                onBackClick = { navController.popBackStack() },
                onCreateClick = { groupName, members ->
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.MessengerHome.route) {
            MessengerHome(onChatClick = { chatId ->
                // Simplified navigation for demo
                navController.navigate("messenger-conversation/User")
            })
        }
        composable(
            Screen.MessengerConversation.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { entry ->
            val userName = entry.arguments?.getString("userName") ?: "User"
            MessengerConversation(
                userName = userName,
                userProfilePic = "https://api.dicebear.com/7.x/avataaars/svg?seed=$userName",
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
