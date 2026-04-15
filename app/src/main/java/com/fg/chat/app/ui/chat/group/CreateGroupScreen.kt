package com.fg.chat.app.ui.chat.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

data class Friend(
    val id: String,
    val name: String,
    val profilePic: Int,
    var isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    onBackClick: () -> Unit,
    onCreateClick: (String, List<String>) -> Unit
) {
    var groupName by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    
    // Mock Data
    val friendsList = remember {
        mutableStateListOf(
            Friend("1", "Alex Linderson", R.drawable.image_introduce_1),
            Friend("2", "Angelina Jolie", R.drawable.image_introduce_2),
            Friend("3", "John Doe", R.drawable.image_introduce_3),
            Friend("4", "Robert Downey", R.drawable.image_introduce_4),
            Friend("5", "Emma Watson", R.drawable.image_introduce_1),
            Friend("6", "Chris Evans", R.drawable.image_introduce_2)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Group", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        floatingActionButton = {
            if (groupName.isNotBlank() && friendsList.any { it.isSelected }) {
                FloatingActionButton(
                    onClick = { 
                        onCreateClick(groupName, friendsList.filter { it.isSelected }.map { it.id })
                    },
                    containerColor = LightBlue,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Create", tint = White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(White)
        ) {
            // --- Group Info Section ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color(0xFFF5F5F5), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Group Photo", tint = Color.Gray)
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                TextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    placeholder = { Text("Enter group name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = LightBlue
                    )
                )
            }

            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), thickness = 0.5.dp, color = Color.LightGray)

            // --- Search Section ---
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                placeholder = { Text("Search friends") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = LightBlue,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Text(
                text = "Select Members",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            // --- Friends List ---
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(friendsList.filter { it.name.contains(searchQuery, ignoreCase = true) }) { friend ->
                    SelectableFriendItem(
                        friend = friend,
                        onSelectedChange = { isSelected ->
                            val index = friendsList.indexOf(friend)
                            if (index != -1) {
                                friendsList[index] = friend.copy(isSelected = isSelected)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SelectableFriendItem(
    friend: Friend,
    onSelectedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectedChange(!friend.isSelected) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = friend.profilePic),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = friend.name,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        
        Checkbox(
            checked = friend.isSelected,
            onCheckedChange = onSelectedChange,
            colors = CheckboxDefaults.colors(checkedColor = LightBlue)
        )
    }
}
