package com.example.messager.Database

import androidx.room.Database
import com.example.messager.Database.Entities.User
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.messager.Database.Dao.GroupDao
import com.example.messager.Database.Dao.GroupMemberDao
import com.example.messager.Database.Dao.MessageDao
import com.example.messager.Database.Dao.UserDao
import com.example.messager.Database.Entities.GroupMember
import com.example.messager.Database.Entities.Group
import com.example.messager.Database.Entities.Message

@Database(entities = [User::class, Group::class, GroupMember::class, Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun groupDao(): GroupDao
    abstract fun groupMemberDao(): GroupMemberDao
    abstract fun messageDao(): MessageDao

    companion object {
        private const val DATABASE_NAME = "Application_Messager"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
