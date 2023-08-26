package com.example.messager.Database.Dao

import androidx.room.*
import com.example.messager.Database.Entities.Group
import com.example.messager.Database.Entities.GroupMember


@Dao
interface GroupDao {
    @Insert
    suspend fun insertGroup(group: Group): Long

    @Update
    suspend fun updateGroup(group: Group)

    @Delete
    suspend fun deleteGroup(group: Group)

    @Query("SELECT * FROM group_table")
    suspend fun getAllGroups(): List<Group>

    @Insert
    suspend fun insertGroupMember(groupMember: GroupMember)
}


