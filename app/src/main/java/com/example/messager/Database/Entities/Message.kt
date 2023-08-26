package com.example.messager.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val messageId: Long = 0,
    val groupId: Long,
    val senderUserId: Long,
    val content: String,
    val timestamp: Long
)

