package com.fg.chat.app.nav

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object OtpVerify : Screen("otp-verify")
    object Onboarding : Screen("onboarding")
    object Introduce : Screen("introduce")
    object Conversation : Screen("conversation/{user}")
    object Chat : Screen("chat-list")
    object HelpCenter : Screen("help-center")
    object InviteFriend : Screen("invite-friend")
    object More : Screen("more")
    object Other : Screen("other")
    object Security : Screen("security")
}