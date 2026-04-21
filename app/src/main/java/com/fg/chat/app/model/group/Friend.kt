package com.fg.chat.app.model.group

data class Friend(
    val id: String,
    val name: String,
    val profilePic: Int,
    var isSelected: Boolean = false
)