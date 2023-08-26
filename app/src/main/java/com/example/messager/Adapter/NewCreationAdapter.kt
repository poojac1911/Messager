package com.example.messager.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.NewCreationModel
import com.example.messager.databinding.ListItemAllUserBinding

class NewCreationAdapter(private var data: List<NewCreationModel>) :
    RecyclerView.Adapter<NewCreationAdapter.ViewHolder>() {

    private var itemClickListener: ((NewCreationModel, RadioButton) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAllUserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ListItemAllUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewCreationModel) {
            binding.userImg.text = item.userName.firstOrNull()?.uppercase().toString()
            binding.tvName.text = item.userName
            binding.number.text = item.phoneNumber

            binding.radioButton.visibility = if (item.isRadioButtonVisible) View.VISIBLE else View.GONE
            binding.radioButton.isChecked = item.isRadioButtonChecked

            binding.radioButton.setOnCheckedChangeListener { _, isChecked ->
                item.isRadioButtonChecked = isChecked
                if (isChecked) {
                    itemClickListener?.invoke(item, binding.radioButton)
                }
            }

            binding.root.setOnClickListener {
                itemClickListener?.invoke(item, binding.radioButton)
            }
        }
    }

    // Update the data and refresh the RecyclerView
    fun updateData(newData: List<NewCreationModel>) {
        data = newData
        notifyDataSetChanged()
    }

    fun showRadioButtons() {
        data.forEach { it.isRadioButtonVisible = true }
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (NewCreationModel, RadioButton) -> Unit) {
        itemClickListener = listener
    }
}
