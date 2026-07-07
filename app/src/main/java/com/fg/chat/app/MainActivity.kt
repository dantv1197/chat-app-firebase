package com.fg.chat.app

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import android.annotation.SuppressLint
import android.os.Bundle
import com.fg.chat.app.nav.AppNavigation
import com.fg.chat.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
    @Composable
    fun MainScreen() {
        val navController = rememberNavController() // Initialize NavController at the top level of the UI

        Scaffold(
            bottomBar = { /* Place BottomNavigation here if needed */ }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                // Call the separated Navigation file from step 2
                AppNavigation(navController = navController)
            }
        }
    }
}
