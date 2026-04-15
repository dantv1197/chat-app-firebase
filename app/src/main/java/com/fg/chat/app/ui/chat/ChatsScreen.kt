package com.fg.chat.app.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.R
import com.fg.chat.app.ui.theme.LightBlue
import com.fg.chat.app.ui.theme.White

data class ChatItem(
    val id: String,
    val name: String,
    val lastMessage: String,
    val time: String,
    val profilePic: Int,
    val isOnline: Boolean = false,
    val unreadCount: Int = 0
)

@Composable
fun ChatsScreen(
    onChatClick: (String) -> Unit,
    onAddClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAddFriendClick: () -> Unit,
    onCreateGroupClick: () -> Unit
) {
    // Mock Data
    val chatList = listOf(
        ChatItem("1", "Alex Linderson", "How are you today?", "2 min ago", R.drawable.image_introduce_1, true, 3),
        ChatItem("2", "Angelina Jolie", "See you tomorrow!", "10 min ago", R.drawable.image_introduce_2),
        ChatItem("3", "John Doe", "Thanks for the help", "1 hour ago", R.drawable.image_introduce_3),
        ChatItem("4", "Robert Downey", "Let's meet at 5", "Yesterday", R.drawable.image_introduce_4)
    )

    Box(modifier = Modifier.fillMaxSize().background(White)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // --- App Bar ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 48.dp, 24.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color.LightGray.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
                }

                Text(
                    text = "Home",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_introduce_1),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // --- Stories/Active Users ---
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(58.dp)
                                .background(Color.LightGray.copy(alpha = 0.2f), CircleShape)
                                .clickable { onAddClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Story", tint = Color.Black)
                        }
                        Text("My Status", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                    }
                }
                items(chatList) { chat ->
                    StoryItem(chat)
                }
            }

            // --- Chat List Container ---
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color(0xFFF5F5F5))
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // --- Action Cards for Add Friend and Create Group ---
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            ChatActionCard(
                                icon = Icons.Default.Person,
                                title = "Add Friend",
                                backgroundColor = Color(0xFFE3F2FD),
                                onClick = onAddFriendClick,
                                modifier = Modifier.weight(1f)
                            )
                            ChatActionCard(
                                icon = Icons.Default.Add,
                                title = "Create Group",
                                backgroundColor = Color(0xFFF3E5F5),
                                onClick = onCreateGroupClick,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    item {
                        Text(
                            text = "Recent Chats",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    items(chatList) { chat ->
                        ChatItemRow(chat, onChatClick)
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = onAddClick,
            containerColor = LightBlue,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Chat", tint = White)
        }
    }
}

@Composable
fun ChatActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.height(90.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = LightBlue,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun StoryItem(chat: ChatItem) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .padding(2.dp)
                .background(White, CircleShape)
                .padding(2.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = chat.profilePic),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            if (chat.isOnline) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color.Green, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }
        }
        Text(chat.name.split(" ")[0], fontSize = 12.sp, color = Color.Black, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun ChatItemRow(chat: ChatItem, onChatClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChatClick(chat.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = chat.profilePic),
            contentDescription = null,
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = chat.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(text = chat.lastMessage, color = Color.Gray, fontSize = 14.sp)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = chat.time, color = Color.Gray, fontSize = 12.sp)
            if (chat.unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(20.dp)
                        .background(Color.Red, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = chat.unreadCount.toString(), color = White, fontSize = 10.sp)
                }
            }
        }
    }
}
