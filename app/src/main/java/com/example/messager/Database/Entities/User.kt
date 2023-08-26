package com.example.messager.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    val userName: String,
    val phoneNumber: String
)
