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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.NewCreationModel
import com.example.messager.Adapter.NewCreationAdapter
import com.example.messager.Database.AppDatabase
import com.example.messager.Database.Dao.UserDao
import com.example.messager.Database.Entities.GroupMember
import com.example.messager.Model.UserDetails
import com.example.messager.Model.UserSelect
import com.example.messager.databinding.FragmentNewCreationBinding
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class AddingMemberFragment : Fragment() {

    private var _binding: FragmentNewCreationBinding? = null
    private val binding get() = _binding!!

    private lateinit var userDao: UserDao
    private lateinit var userSelect: UserSelect
    private var groupId by Delegates.notNull<Long>()

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

        // Get the groupId from the arguments
        val args: AddingMemberFragmentArgs by navArgs()
        groupId = args.groupId

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
            val userId = model.userId

            // Store the user's information along with groupId in the groupmember_table
            lifecycleScope.launch {
                val groupMember = GroupMember(groupId, userId)
                AppDatabase.getInstance(requireContext()).groupMemberDao().insertGroupMember(groupMember)
            }

            val message = "Added ${model.userName}'s Successfully"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()
        }

        adapter.showRadioButtons()
        binding.extendedFabStartChat.isVisible = false

        val navController = findNavController()
        val currentDestination = navController.currentDestination
        currentDestination?.label = "Add New Member"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}