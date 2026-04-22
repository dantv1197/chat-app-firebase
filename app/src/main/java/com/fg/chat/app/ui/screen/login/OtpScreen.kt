package com.fg.chat.app.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.ui.keypad.KeypadButton
import com.fg.chat.app.ui.theme.GradientLightBlue
import com.fg.chat.app.ui.theme.LightBlue
import com.fg.chat.app.ui.theme.White

@Composable
fun OtpScreen(
    onBack: () -> Unit,
    onNext: (String) -> Unit
) {
    var otpCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Space at the top
        Spacer(modifier = Modifier.height(100.dp))

        // Timer and Resend Code
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountBox,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "00 : 45",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextButton(onClick = { /* Handle Resend */ }) {
                Text(
                    text = "Resend Code",
                    fontSize = 18.sp,
                    color = LightBlue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        // OTP Input Fields
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(4) { index ->
                OtpDigit(
                    digit = if (index < otpCode.length) otpCode[index].toString() else "",
                    isFocused = index == otpCode.length
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Next Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Surface(
                shape = CircleShape,
                color = GradientLightBlue,
                modifier = Modifier.size(64.dp),
                onClick = { if (otpCode.length == 4) onNext(otpCode) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Keypad
        KeypadButton(
            onKeyClick = {
                if (otpCode.length < 4) {
                    otpCode += it
                }
            },
            onDeleteClick = {
                if (otpCode.isNotEmpty()) {
                    otpCode = otpCode.dropLast(1)
                }
            }
        )
    }
}

@Composable
fun OtpDigit(
    digit: String,
    isFocused: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(60.dp)
    ) {
        Text(
            text = digit,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.height(48.dp)
        )
        HorizontalDivider(
            thickness = 3.dp,
            color = if (isFocused) GradientLightBlue else Color.DarkGray
        )
    }
}
