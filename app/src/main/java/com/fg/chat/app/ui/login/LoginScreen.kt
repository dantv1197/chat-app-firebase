package com.fg.chat.app.ui.login

import android.util.Log
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.fg.chat.app.keypad.ui.KeypadButton
import com.fg.chat.app.ui.theme.GradientLightBlue
import com.fg.chat.app.ui.theme.PaleCyan
import com.fg.chat.app.ui.theme.White

val phoneRegex: Regex = Regex("^\\+?[1-9]\\d{1,14}\$")

//https://www.figma.com/design/0x2AQY5fs270EqaJeb1gUp/Chatting-App-UI-Kit-Design-%7C-E-Chat-%7C-Figma--Community-?node-id=37-4482&t=1jKBDwBRMiO5LHdX-0
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
        // --- Header màu xanh ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(if (isVisible) 0.28f else 0.35F)
                .clip(RoundedCornerShape(bottomStart = if (isVisible) 0.dp else 80.dp))
                .background(Color(0xFF34A8EB))
                .padding(24.dp)
        ) {
            Text(
                text = "Login",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp)
            )

            Button(
                onClick = { onNavigateToRegister.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = White.copy(alpha = 0.8f)),
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp)
            ) {
                Text("Register", color = Color(0xFF4A6572))
            }

            Text(
                text = "Enter your mobile phone",
                color = Color.White,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 24.dp)
            )
        }

        // --- Phần nội dung nhập liệu ---
        Spacer(modifier = if (isVisible) Modifier.weight(1f) else Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp),
        ) {
            Column(

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "You will get a code via sms.",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind() { // Vẽ đường kẻ ngang phía dưới
                            val strokeWidth = 2f
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                Color.Black,
                                Offset(0f, y),
                                Offset(size.width, y),
                                strokeWidth
                            )
                        }
                        .padding(bottom = 8.dp)
                ) {
                    // Giả lập chọn quốc gia
                    Text("🇬🇧", fontSize = 24.sp)
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("(+44)", color = Color.Gray, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = inputNumber,

                        modifier = Modifier
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    isVisible = true

                                }
                            },
                        decorationBox = { innerTextField ->
                            if (inputNumber.isEmpty()) {
                                Text("00 0000 0000", color = Color.Gray)
                            }
                            innerTextField() // Bắt buộc gọi hàm này
                        },
                        // block system keyboard
                        readOnly = true,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() || it == '+' }) {
                                inputNumber = input
                            }
                            isEnableNext = inputNumber.length >= 10
                        },
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Gray)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Checkbox và Nút mũi tên
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { it -> isChecked = it })
                        Text("Remember me", color = Color.Black)
                    }

                    Surface(
                        shape = CircleShape,
                        color = if (isEnableNext) GradientLightBlue else PaleCyan,
                        enabled = isEnableNext,
                        modifier = Modifier.size(56.dp),
                        onClick = { onNavigateToNext.invoke(inputNumber) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            KeypadButton(
                onKeyClick = {
                    Log.e("Click_edit", "$it")
                    inputNumber += it
                },
                onDeleteClick = { inputNumber.dropLast(inputNumber.length - 1) }
            )
        }
    }
}
