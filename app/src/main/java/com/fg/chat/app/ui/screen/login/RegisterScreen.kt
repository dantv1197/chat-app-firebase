package com.fg.chat.app.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.ui.theme.GradientLightBlue
import com.fg.chat.app.ui.theme.LightBlue
import com.fg.chat.app.ui.theme.PaleCyan
import com.fg.chat.app.ui.theme.White

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onNavigateToNext: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // --- Header Section ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .clip(RoundedCornerShape(bottomStart = 100.dp))
                .background(LightBlue)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = White
                        )
                    }

                    Button(
                        onClick = { /* Already on Register */ },
                        colors = ButtonDefaults.buttonColors(containerColor = White.copy(alpha = 0.2f)),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Login", color = White, fontWeight = FontWeight.SemiBold)
                    }
                }

                Text(
                    text = "Create your\naccount",
                    color = White,
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }

        // --- Content Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)
        ) {
            // Name Field
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                placeholder = { Text("Enter your name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = LightBlue,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email Field
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                placeholder = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = LightBlue,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Next Button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Surface(
                    shape = CircleShape,
                    color = if (name.isNotEmpty() && email.isNotEmpty()) GradientLightBlue else PaleCyan,
                    enabled = name.isNotEmpty() && email.isNotEmpty(),
                    modifier = Modifier.size(64.dp),
                    onClick = { onNavigateToNext(name, email) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
