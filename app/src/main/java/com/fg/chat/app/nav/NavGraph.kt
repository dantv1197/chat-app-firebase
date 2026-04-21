package com.fg.chat.app.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fg.chat.app.model.path.NavPath
import com.fg.chat.app.ui.chat.ChatsScreen
import com.fg.chat.app.ui.chat.converation.ConversationScreen
import com.fg.chat.app.ui.chat.group.CreateGroupScreen
import com.fg.chat.app.ui.login.LoginScreen
import com.fg.chat.app.ui.login.OtpScreen
import com.fg.chat.app.ui.login.RegisterScreen
import com.fg.chat.app.ui.more.HelpCenterScreen
import com.fg.chat.app.ui.more.InviteFriendScreen
import com.fg.chat.app.ui.more.MoreScreen
import com.fg.chat.app.ui.more.OthersScreen
import com.fg.chat.app.ui.more.SecurityScreen
import com.fg.chat.app.ui.onboarding.IntroduceScreen
import com.fg.chat.app.ui.onboarding.OnboardingScreen
import com.fg.chat.app.ui.profile.ProfileScreen
import com.google.gson.Gson

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
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
            OtpScreen(onBack = {}, onNext = {})
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
                onBackClick = {})
        }
        composable(Screen.Chat.route) {
            ChatsScreen(
                onChatClick = {},
                onAddClick = {},
                onSearchClick = {},
                onAddFriendClick = {}
            ) { }
        }
        composable(Screen.HelpCenter.route) {
            HelpCenterScreen(
                onBackClick = {}
            )
        }
        composable(Screen.InviteFriend.route) {
            InviteFriendScreen(
                onBackClick = {}
            )
        }
        composable(Screen.More.route) {
            MoreScreen(
                onBackClick = {},
                onInviteFriendClick = {},
                onSecurityClick = {},
                onHelpCenterClick = {}
            )
        }
        composable(Screen.Other.route) {
            OthersScreen(
                onBackClick = {}
            )
        }
        composable(Screen.Security.route) {
            SecurityScreen(
                onBackClick = {},
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
    }
}
