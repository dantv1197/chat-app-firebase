package com.fg.chat.app.ui.screen.chat

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
import androidx.compose.material3.MaterialTheme
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
import com.fg.chat.app.model.message.Message
import com.fg.chat.app.model.message.MessageType
import com.fg.chat.app.model.user.Status
import com.fg.chat.app.model.user.User

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
        Message("1", User(), "How are you today?", 10233265656, MessageType.TEXT, true),
        Message("2", User(), "See you tomorrow!", 10233265656, MessageType.TEXT,false),
        Message("3", User(), "Thanks for the help", 10233265656, MessageType.TEXT,false),
        Message("4", User(), "Let's meet at 5", 10233265656, MessageType.TEXT,false)
    )

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
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
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurface)
                }

                Text(
                    text = "Home",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
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
                                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                .clickable { onAddClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Story", tint = MaterialTheme.colorScheme.onSurface)
                        }
                        Text("My Status", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
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
                    .background(MaterialTheme.colorScheme.background)
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
                                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                onClick = onAddFriendClick,
                                modifier = Modifier.weight(1f)
                            )
                            ChatActionCard(
                                icon = Icons.Default.Add,
                                title = "Create Group",
                                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
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
                            color = MaterialTheme.colorScheme.onBackground
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
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Chat")
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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun StoryItem(chat: Message) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .padding(2.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .padding(2.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = chat.senderId.photoUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            if (chat.senderId.status == Status.ONLINE) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color.Green, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }
        }
        Text(chat.name.split(" ")[0], fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun ChatItemRow(chat: Message, onChatClick: (String) -> Unit) {
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
            Text(text = chat.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
            Text(text = chat.lastMessage, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = chat.time, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            if (chat.unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(20.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = chat.unreadCount.toString(), color = MaterialTheme.colorScheme.onPrimary, fontSize = 10.sp)
                }
            }
        }
    }
}