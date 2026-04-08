package com.fg.chat.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fg.chat.app.ui.login.LoginScreen
import com.fg.chat.app.ui.onboarding.IntroduceScreen
import com.fg.chat.app.ui.onboarding.OnboardingScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object OtpVerify : Screen("otp-verify")
    object Onboarding : Screen("onboarding")
    object Introduce : Screen("introduce")


}

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
    }
}