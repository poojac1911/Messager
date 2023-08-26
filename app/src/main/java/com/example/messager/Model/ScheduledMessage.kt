package com.example.messager.Model

import java.util.*

data class ScheduledMessage(
    val content: String,
    val scheduledTime: Calendar,
    val repeatInterval: Long = 0
)

