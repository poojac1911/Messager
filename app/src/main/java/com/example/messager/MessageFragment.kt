package com.example.messager

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messager.Adapter.MessageAdapter
import com.example.messager.Model.MessageModel
import com.example.messager.Model.ScheduledMessage
import com.example.messager.databinding.FragmentMessageComposeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


class MessageFragment : Fragment() {

    private val messageList = mutableListOf<MessageModel>()
    private lateinit var messageAdapter: MessageAdapter

    private var _binding: FragmentMessageComposeBinding? = null
    private val binding get() = _binding!!

    // Declare the variables at the top
    private var groupId by Delegates.notNull<Long>()
    private lateinit var groupName: String
    private lateinit var groupDescription: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageComposeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args: MessageFragmentArgs by navArgs()
        groupId = args.groupId
        groupName = args.groupName
        groupDescription = args.groupDescription

//        val groupTitleTextView: TextView = view.findViewById(R.id.nameptv)
//        groupTitleTextView.text = groupName

        setHasOptionsMenu(true) // Enable options menu

        // Initialize RecyclerView and MessageAdapter
        messageAdapter = MessageAdapter(messageList)
        binding.chatRecyclerView.adapter = messageAdapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.sendmsg.setOnClickListener {
            val messageContent = binding.messaget.text.toString()
            if (messageContent.isNotEmpty()) {
                val currentTime = getCurrentTime()
                val isSender = true
                val message = MessageModel(messageContent, currentTime, isSender)
                messageList.add(message)
                messageAdapter.notifyItemInserted(messageList.size - 1)
                binding.messaget.text.clear()

            }
        }

        binding.attachbtn.setOnClickListener {
            val messageContent = binding.messaget.text.toString()
            val dialogFragment = TimeDatePickerDialogFragment { selectedTime ->
                scheduleMessage(selectedTime, messageContent)
            }
            dialogFragment.show(childFragmentManager, "timeDatePicker")
            binding.messaget.text.clear()
        }
    }

    private fun scheduleMessage(selectedTime: Long, messageContent: String) {
        val currentTime = System.currentTimeMillis()
        if (selectedTime > currentTime) {

            val context = requireContext()

            val intent = Intent(context, AlarmReceiver::class.java)
            // Add necessary extras to the intent if needed

            val pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, selectedTime, pendingIntent
            )

            val delayMillis = selectedTime - currentTime

            lifecycleScope.launch {
                if (delayMillis > 0) {
                    kotlinx.coroutines.delay(delayMillis)
                }
                if (messageContent.isNotEmpty()) {
                    val currentTime = getCurrentTime()
                    val isSender = true
                    val message = MessageModel(messageContent, currentTime, isSender)
                    messageList.add(message)
                    messageAdapter.notifyItemInserted(messageList.size - 1)
                    binding.messaget.text.clear()
                }
            }

            Toast.makeText(context, "Reminder scheduled successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Selected time has already passed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return String.format("%02d:%02d", hour, minute)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_message, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_details -> {
                val action = MessageFragmentDirections.actionMessageFragmentToInfoFragment(
                    groupId, groupName, groupDescription
                )
                findNavController().navigate(action)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}