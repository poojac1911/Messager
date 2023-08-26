package com.example.messager.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.messager.Database.Entities.Group
import com.example.messager.MessageFragment
import com.example.messager.R
import com.example.messager.StartFragmentDirections
import com.example.messager.databinding.ListItemMessageBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class StartAdapter(private var data: List<Group>) :
    RecyclerView.Adapter<StartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMessageBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<Group>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val group = data[adapterPosition]
                val action = StartFragmentDirections.actionStartFragmentToMessageFragment(
                    group.groupId, group.groupName, group.groupDescription
                )
                binding.root.findNavController().navigate(action)
            }
        }

        fun bind(item: Group) {
            binding.imgAdd.text = item.groupName.firstOrNull()?.uppercase().toString()
            binding.tvName.text = item.groupName
            binding.tvMessage.text = item.groupDescription
            binding.imgNomsg.text = item.groupId.toString()

            val formattedDay = getFormattedDay()
            binding.tvDay.text = formattedDay
        }

    }
    fun getFormattedDay(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dateFormat = SimpleDateFormat("EEE", Locale.US)
        return dateFormat.format(calendar.time)
    }

    interface OnItemClickListener {
        fun onItemClick(group: Group)
    }
}




