package com.fg.chat.messenger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fg.chat.messenger.model.MessageStatus
import com.fg.chat.messenger.model.UserStatus

@Composable
fun StatusIndicator(status: UserStatus, modifier: Modifier = Modifier) {
    val color = when (status) {
        UserStatus.ONLINE -> Color.Green
        UserStatus.AWAY -> Color.Yellow
        UserStatus.BUSY -> Color.Red
        UserStatus.OFFLINE -> Color.Gray
    }
    Box(
        modifier = modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(color)
            .background(Color.White.copy(alpha = 0.2f)) // Optional border-like effect
    )
}

@Composable
fun MessageStatusIcon(status: MessageStatus, modifier: Modifier = Modifier) {
    when (status) {
        MessageStatus.SENT -> Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Sent",
            modifier = modifier.size(16.dp),
            tint = Color.Gray
        )
        MessageStatus.DELIVERED -> Icon(
            imageVector = Icons.Default.DoneAll,
            contentDescription = "Delivered",
            modifier = modifier.size(16.dp),
            tint = Color.Gray
        )
        MessageStatus.READ -> Icon(
            imageVector = Icons.Default.DoneAll,
            contentDescription = "Read",
            modifier = modifier.size(16.dp),
            tint = Color(0xFF00B0FF)
        )
    }
}
