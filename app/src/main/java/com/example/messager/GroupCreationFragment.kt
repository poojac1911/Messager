package com.example.messager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kotlin.NewCreationModel
import com.example.messager.Database.AppDatabase
import com.example.messager.Database.Dao.GroupDao
import com.example.messager.Database.Entities.Group
import com.example.messager.Database.Entities.GroupMember
import com.example.messager.Model.UserSelectionViewModel
import com.example.messager.Model.UserSelect
import com.example.messager.databinding.FragmentGroupCreationBinding
import kotlinx.coroutines.launch

class GroupCreationFragment : Fragment() {

    private var _binding: FragmentGroupCreationBinding? = null
    private val binding get() = _binding!!

    private lateinit var userSelectionViewModel: UserSelectionViewModel
    private lateinit var userSelect: UserSelect
    private lateinit var groupDao: GroupDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userSelectionViewModel = ViewModelProvider(requireActivity()).get(UserSelectionViewModel::class.java)
        userSelect = ViewModelProvider(requireActivity()).get(UserSelect::class.java)
        groupDao = AppDatabase.getInstance(requireContext()).groupDao()

        // Access the selected users' information
        val selectedUsers = userSelect.selectedUsers

        val selectedUserData = selectedUsers.joinToString("\n") { user ->
            "Username: ${user.userName}\nPhone Number: ${user.phoneNumber}\n"
        }

//        binding.groupMember.text = selectedUserData

        binding.extendedFabStartChat.setOnClickListener {
            val groupName = binding.formProEtTitle.text.toString()
            val groupDescription = binding.formProEtDesc.text.toString()

            if (groupName.isNotEmpty() && groupDescription.isNotEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val groupId = insertGroup(groupName, groupDescription)
                    storeGroupMembers(groupId, selectedUsers)

                    findNavController().navigate(R.id.action_GroupCreationFragment_to_startFragment)
                    Toast.makeText(requireContext(), "Created Group Successfully ", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (groupName.isEmpty()) {
                    Toast.makeText(requireContext(), "Group name cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Group description cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun insertGroup(groupName: String, groupDescription: String): Long {
        val group = Group(groupName = groupName, groupDescription = groupDescription)
        return groupDao.insertGroup(group)
    }

    private suspend fun storeGroupMembers(groupId: Long, selectedUsers: MutableSet<NewCreationModel>) {
        val groupMemberDao = AppDatabase.getInstance(requireContext()).groupMemberDao()

        selectedUsers.forEach { user ->
            val groupMember = GroupMember(groupId = groupId, userId = user.userId)
            groupMemberDao.insertGroupMember(groupMember)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
