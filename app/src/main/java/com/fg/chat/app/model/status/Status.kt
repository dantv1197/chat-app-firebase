package com.fg.chat.app.model.status

class Status {
    enum class User {
        AVAILABLE,
        UNAVAILABLE,
        BUSY,
        CALLING
    }
    enum class Message{
        SENDING,
        SENT,
        DELIVERED,
        READ
    }
}
