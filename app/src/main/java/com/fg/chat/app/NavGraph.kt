package com.fg.chat.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fg.chat.app.ui.onboarding.IntroduceScreen
import com.fg.chat.app.ui.onboarding.OnboardingScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable ("onboarding") {
            OnboardingScreen(
                onFinish = {
                    navController.navigate("introduce") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        composable("introduce") {
            IntroduceScreen()
        }
    }
}