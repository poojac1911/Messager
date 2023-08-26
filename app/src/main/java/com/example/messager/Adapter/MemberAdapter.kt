package com.example.messager.Adapter

import java.lang.reflect.Member
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messager.Database.Entities.User
import com.example.messager.R
import com.example.messager.databinding.ListItemGroupMemberBinding
import kotlin.random.Random


class MemberAdapter : ListAdapter<User, MemberAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGroupMemberBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ListItemGroupMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            binding.imgAdd.text = item.userName.firstOrNull()?.uppercase().toString()
            binding.tvName.text = item.userName
            binding.number.text = item.phoneNumber

        }
    }

    private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}

