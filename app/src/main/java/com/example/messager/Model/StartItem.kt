package com.example.messager.Model

import com.example.kotlin.StartModel
import com.example.messager.Database.Entities.Group

sealed class StartItem {
    data class GroupItem(val groupTitle: String) : StartItem()
    data class ChatItem(val chatData: StartModel) : StartItem()
}
// Update StartItem.GroupItem class
data class GroupItem(val groupId: Int, val groupTitle: String) : StartItem()

