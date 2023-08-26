package com.example.messager.Model

import androidx.lifecycle.ViewModel
import com.example.kotlin.NewCreationModel

class UserSelect : ViewModel() {
    val selectedUsers: MutableSet<NewCreationModel> = mutableSetOf()
    val selectedUserDetails: MutableMap<String, UserDetails> = mutableMapOf()

    private val selectedMember: MutableMap<Long, MutableSet<Long>> = mutableMapOf()

    fun addUser(userId: Long, groupId: Long) {
        if (!selectedMember.containsKey(userId)) {
            selectedMember[userId] = mutableSetOf()
        }
        selectedMember[userId]?.add(groupId)
    }

    fun isUserSelected(userId: Long, groupId: Long): Boolean {
        return selectedMember[userId]?.contains(groupId) == true
    }
}

data class UserDetails(val userId: Long, val username:String, val phoneNumber: String)