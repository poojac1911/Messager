package com.example.messager.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey(autoGenerate = true)
    val groupId: Long = 0,
    val groupName: String,
    val groupDescription: String
)
