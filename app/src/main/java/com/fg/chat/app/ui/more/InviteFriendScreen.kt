package com.fg.chat.app.ui.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.R
import com.fg.chat.app.model.more.Contact
import com.fg.chat.app.ui.theme.LightBlue
import com.fg.chat.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteFriendScreen(
    onBackClick: () -> Unit
) {
    val contacts = listOf(
        Contact("1", "Alex Linderson", "+1 234 567 890", R.drawable.image_introduce_1),
        Contact("2", "Angelina Jolie", "+1 987 654 321", R.drawable.image_introduce_2),
        Contact("3", "John Doe", "+1 555 000 111", R.drawable.image_introduce_3),
        Contact("4", "Robert Downey", "+1 444 222 333", R.drawable.image_introduce_4)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Invite Friends", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(White)
        ) {
            // --- Invite Link Section ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Share Invite Link",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Invite your friends to join ChatFirebase and start messaging!",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "chatfirebase.com/invite/alex",
                            modifier = Modifier.weight(1f),
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        Icon(
                            imageVector = Icons.Default.Add, // Placeholder for Copy icon
                            contentDescription = "Copy",
                            tint = LightBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { /* Share action */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share Link")
                    }
                }
            }

            Text(
                text = "Contacts",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(contacts) { contact ->
                    ContactItem(contact)
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = contact.profilePic),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(text = contact.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = contact.phoneNumber, fontSize = 12.sp, color = Color.Gray)
        }
        
        OutlinedButton(
            onClick = { /* Invite */ },
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, LightBlue),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = LightBlue)
        ) {
            Text("Invite", fontSize = 12.sp)
        }
    }
}
