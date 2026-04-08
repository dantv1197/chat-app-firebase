package com.fg.chat.app.keypad.model

data class KeypadButton(
    val mainText: String,
    val subText: String = "",
    val isAction: Boolean = false
)
