package com.fg.chat.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fg.chat.app.nav.AppNavigation
import com.fg.chat.app.ui.theme.ChatappTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatappTheme {
                MainScreen()
            }
        }
    }
    @Composable
    fun MainScreen() {
        val navController = rememberNavController() // Khởi tạo NavController ở cấp cao nhất của UI

        Scaffold(
            bottomBar = { /* Nếu bạn có BottomNavigation thì đặt ở đây */ }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                // Gọi file Navigation đã tách ở bước 2
                AppNavigation(navController = navController)
            }
        }
    }
}