package com.example.messager.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messager.Model.MessageModel
import com.example.messager.databinding.ListItemSendChatBinding

class MessageAdapter(private val messages: List<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSendChatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    inner class ViewHolder(private val binding: ListItemSendChatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MessageModel) {
            binding.messageText.text = item.content
            binding.timeText.text = item.time
        }
    }
}
