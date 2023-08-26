package com.example.messager.Database.Dao

import androidx.room.*
import com.example.messager.Database.Entities.GroupMember


@Dao
interface GroupMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupMember(groupMember: GroupMember)

    @Query("SELECT userId FROM group_member_table WHERE groupId = :groupId")
    suspend fun getUserIdsForGroup(groupId: Long): List<Long>

    @Delete
    suspend fun deleteGroupMember(groupMember: GroupMember)

    @Query("DELETE FROM group_member_table WHERE groupId = :groupId AND userId = :userId")
    suspend fun removeUserFromGroup(groupId: Long, userId: Long)

}


