package com.example.messager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.NewCreationModel
import com.example.messager.Adapter.NewCreationAdapter
import com.example.messager.Database.AppDatabase
import com.example.messager.Database.Dao.UserDao
import com.example.messager.Model.UserDetails
import com.example.messager.Model.UserSelect
import com.example.messager.databinding.FragmentNewCreationBinding
import kotlinx.coroutines.launch

class NewCreationFragment : Fragment() {

    private var _binding: FragmentNewCreationBinding? = null
    private val binding get() = _binding!!

    private lateinit var userDao: UserDao

    private val selectedRadioButtons = mutableSetOf<RadioButton>()
    private lateinit var userSelect: UserSelect


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        userSelect = ViewModelProvider(requireActivity()).get(UserSelect::class.java)

        userDao = AppDatabase.getInstance(requireContext()).userDao()

        binding.recyclerViewStartChat.layoutManager = LinearLayoutManager(requireContext())

        val adapter = NewCreationAdapter(emptyList())
        binding.recyclerViewStartChat.adapter = adapter

        // Fetch user data from the database
        lifecycleScope.launch {
            val userList = userDao.getAllUsers()
            val chatList = userList.map { user ->
                NewCreationModel(user.userId, user.userName, user.phoneNumber)
            }
            adapter.updateData(chatList)
        }

        adapter.setOnItemClickListener { model, radioButton ->
            val message = "Clicked on ${model.userName}'s chat"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

            if (radioButton.isChecked) {
                selectedRadioButtons.add(radioButton)
                userSelect.selectedUsers.add(model)
                // Store selected user details in ViewModel
                userSelect.selectedUserDetails[model.userName] = UserDetails(model.userId, model.userName, model.phoneNumber)
            } else {
                selectedRadioButtons.remove(radioButton)
                userSelect.selectedUsers.remove(model)
                findNavController().navigate(R.id.action_NewCreationFragment_to_MessageFragment)
            }
        }

        binding.extendedFabStartChat.setOnClickListener {

            adapter.showRadioButtons()
            binding.extendedFabStartChat.isVisible = false
            binding.extendedFabNext.isVisible = true

            // Get the current destination and update its label
            val navController = findNavController()
            val currentDestination = navController.currentDestination
            currentDestination?.label = "New Group Conversation"
        }

        binding.extendedFabNext.setOnClickListener {

            if (selectedRadioButtons.size < 2) {
                Toast.makeText(requireContext(), "Please select at least 2 Members", Toast.LENGTH_SHORT).show()
            } else {
                // Perform the action to navigate to GroupCreationFragment here
                findNavController().navigate(R.id.action_NewCreationFragment_to_GroupCreationFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}