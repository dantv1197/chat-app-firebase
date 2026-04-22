package com.fg.chat.app.model.keypad

data class KeypadButton(
    val mainText: String,
    val subText: String = "",
    val isAction: Boolean = false
)