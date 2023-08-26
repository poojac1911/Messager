package com.example.messager.Database

import com.example.messager.Database.Dao.UserDao
import com.example.messager.Database.Entities.User

class UserRepository(private val userDao: UserDao) {

    suspend fun getUsersByIds(userIds: List<Long>): List<User> {
        return userDao.getUsersByIds(userIds)
    }

    suspend fun getUserById(userId: Long): User? {
        return userDao.getUserById(userId)
    }

}
