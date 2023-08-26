package com.example.messager.Database.Dao

import androidx.room.*
import com.example.messager.Database.Entities.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM user_table WHERE userId IN (:userIds)")
    suspend fun getUsersByIds(userIds: List<Long>): List<User>

    @Query("SELECT * FROM user_table WHERE userId = :userId")
    suspend fun getUserById(userId: Long): User?

}
