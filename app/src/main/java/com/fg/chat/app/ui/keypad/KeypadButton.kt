package com.fg.chat.app.ui.keypad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.model.keypad.KeypadButton

val keys = listOf(
    KeypadButton("1", ""), KeypadButton("2", "A B C"), KeypadButton("3", "D E F"),
    KeypadButton("4", "G H I"), KeypadButton("5", "J K L"), KeypadButton("6", "M N O"),
    KeypadButton("7", "P Q R S"), KeypadButton("8", "T U V"), KeypadButton("9", "W X Y Z"),
    KeypadButton("", ""), KeypadButton("0", ""), KeypadButton("backspace", "", isAction = true)
)
@Composable
fun KeypadButton(
    onKeyClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(4.dp)
    ) {
        // Split into 4 rows
        keys.chunked(3).forEach { rowKeys ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowKeys.forEach { key ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        if (key.mainText.isNotEmpty() || key.isAction) {
                            KeyButton(key, onKeyClick, onDeleteClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyButton(
    key: KeypadButton,
    onKeyClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    val isBackspace = key.mainText == "backspace"

    Surface(
        onClick = { if (isBackspace) onDeleteClick() else onKeyClick(key.mainText) },
        color = if (isBackspace) Color.Transparent else MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth().defaultMinSize(minHeight = 48.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (isBackspace) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            } else {
                Text(
                    text = key.mainText,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                if (key.subText.isNotEmpty()) {
                    Text(
                        text = key.subText,
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}