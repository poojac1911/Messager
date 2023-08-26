package com.example.messager.Database.Dao

import androidx.room.*
import com.example.messager.Database.Entities.Message


@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("SELECT * FROM message_table WHERE groupId = :groupId")
    suspend fun getMessagesForGroup(groupId: Long): List<Message>

}



