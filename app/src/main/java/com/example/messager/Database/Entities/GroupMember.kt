package com.example.messager.Database.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "group_member_table",
    primaryKeys = ["groupId", "userId"],
    foreignKeys = [
        ForeignKey(entity = Group::class, parentColumns = ["groupId"], childColumns = ["groupId"]),
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"])
    ]
)
data class GroupMember(
    val groupId: Long,
    val userId: Long
)



