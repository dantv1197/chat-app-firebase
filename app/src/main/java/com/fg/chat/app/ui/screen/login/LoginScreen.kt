package com.fg.chat.app.ui.screen.login

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.ui.keypad.KeypadButton
import com.fg.chat.app.ui.theme.GradientLightBlue
import com.fg.chat.app.ui.theme.LightBlue
import com.fg.chat.app.ui.theme.PaleCyan
import com.fg.chat.app.ui.theme.White

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToNext: (String) -> Unit
) {
    var inputNumber by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }
    var isEnableNext by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    BackHandler(enabled = isVisible) {
        isVisible = false
        focusManager.clearFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // --- Header Section ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(if (isVisible) 0.3f else 0.4f)
                .clip(RoundedCornerShape(bottomStart = if (isVisible) 0.dp else 100.dp))
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
                    Text(
                        text = "Login",
                        color = White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = { onNavigateToRegister() },
                        colors = ButtonDefaults.buttonColors(containerColor = White.copy(alpha = 0.2f)),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("Register", color = White, fontWeight = FontWeight.SemiBold)
                    }
                }

                Text(
                    text = "Enter your\nmobile phone",
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
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You will get a code via sms.",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Phone Number Input
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("🇬🇧", fontSize = 24.sp)
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "(+44)",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    BasicTextField(
                        value = inputNumber,
                        onValueChange = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    isVisible = true
                                }
                            },
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        ),
                        readOnly = true,
                        decorationBox = { innerTextField ->
                            if (inputNumber.isEmpty()) {
                                Text("00 0000 0000", color = Color.LightGray, fontSize = 20.sp)
                            }
                            innerTextField()
                        }
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Remember Me and Next Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = LightBlue,
                            uncheckedColor = Color.LightGray
                        )
                    )
                    Text(
                        text = "Remember me",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = if (inputNumber.length >= 10) GradientLightBlue else PaleCyan,
                    enabled = inputNumber.length >= 10,
                    modifier = Modifier.size(64.dp),
                    onClick = { onNavigateToNext(inputNumber) }
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

        Spacer(modifier = Modifier.weight(1f))

        // Keypad
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            KeypadButton(
                onKeyClick = {
                    if (inputNumber.length < 15) {
                        inputNumber += it
                    }
                },
                onDeleteClick = {
                    if (inputNumber.isNotEmpty()) {
                        inputNumber = inputNumber.dropLast(1)
                    }
                }
            )
        }
    }
}
