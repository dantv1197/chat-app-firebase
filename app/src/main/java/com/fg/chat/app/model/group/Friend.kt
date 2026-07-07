package com.fg.chat.app.model.group

/**
 * Model representing a friend in a selection list.
 *
 * @property id Unique identifier for the friend.
 * @property name Display name of the friend.
 * @property profilePic Profile picture (can be Resource ID or URL String).
 * @property isSelected UI state for selection.
 */
data class Friend(
    val id: String,
    val name: String,
    val profilePic: Any, 
    var isSelected: Boolean = false
)
