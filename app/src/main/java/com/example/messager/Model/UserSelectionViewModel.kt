package com.example.messager.Model

import androidx.lifecycle.ViewModel
import com.example.kotlin.NewCreationModel
import com.example.messager.Database.Entities.User

class UserSelectionViewModel : ViewModel() {

    // List to store selected users
     val selectedUsers: MutableList<User> = mutableListOf()

    fun addUser(user: User) {
        selectedUsers.add(user)
    }

    fun removeUser(user: User) {
        selectedUsers.remove(user)
    }

    fun getSelectedUsersList(): List<User> {
        return selectedUsers
    }

    fun clearSelectedUsers() {
        selectedUsers.clear()
    }
}
