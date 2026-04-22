package com.fg.chat.app.ui.screen.sercurity

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.ui.theme.LightBlue
import com.fg.chat.app.ui.theme.White

@Composable
fun FaceRecognitionScreen(
    onBack: () -> Unit,
    onSkip: () -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // --- Header Section ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .clip(RoundedCornerShape(bottomStart = 100.dp))
                .background(LightBlue)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }

                Text(
                    text = "Face\nRecognition",
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
                .weight(1f)
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Face Recognition Icon Placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(LightBlue.copy(alpha = 0.1f), shape = RoundedCornerShape(60.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = LightBlue
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Use face recognition for faster and safer\naccess to your account.",
                color = Color.Gray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }

        // --- Bottom Buttons ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onSkip) {
                Text(
                    text = "Skip",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = onContinue,
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .height(50.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Continue",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
