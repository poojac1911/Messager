package com.example.messager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.messager.Adapter.MemberAdapter
import com.example.messager.Database.AppDatabase
import com.example.messager.Database.Dao.GroupMemberDao
import com.example.messager.Database.Entities.User
import com.example.messager.Database.UserRepository
import com.example.messager.databinding.FragmentInfoMemberBinding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messager.Adapter.GroupMemberAdapter
import kotlinx.coroutines.launch

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoMemberBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository
    private lateinit var groupMemberDao: GroupMemberDao
    private lateinit var memberAdapter: GroupMemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve arguments
        val args: InfoFragmentArgs by navArgs()
        val groupId = args.groupId
        val groupName = args.groupName
        val groupDescription = args.groupDescription


        binding.groupNameTextView.text = groupName
        binding.groupDescriptionTextView.text = groupDescription

        userRepository = UserRepository(AppDatabase.getInstance(requireContext()).userDao())
        groupMemberDao = AppDatabase.getInstance(requireContext()).groupMemberDao()

        val recyclerView: RecyclerView = binding.recyclerViewGroupMemberList

        memberAdapter = GroupMemberAdapter(groupId)
        recyclerView.adapter = memberAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        memberAdapter.setOnItemClickListener { groupId, userId ->
            showRemoveMemberConfirmationDialog(groupId, userId)
        }

        if (groupId != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                val userIds = groupMemberDao.getUserIdsForGroup(groupId)
                if (userIds.isNotEmpty()) {
                    val users = userRepository.getUsersByIds(userIds)
                    val memberList = mutableListOf<User>()

                    // Fetch user details for each user ID
                    for (userId in userIds) {
                        val user = userRepository.getUserById(userId)
                        user?.let {
                            memberList.add(user)
                        }
                    }
                    memberAdapter.submitList(users) // Update the adapter with new data
                } else { }
            }
        }
        binding.addMember.setOnClickListener {
            val action = InfoFragmentDirections.actionInfoFragmentToAddingMemberFragment(groupId)
            findNavController().navigate(action)
//            findNavController().navigate(R.id.action_infoFragment_to_addingMemberFragment)
        }

    }

    private fun showRemoveMemberConfirmationDialog(groupId: Long, userId: Long) {
        val confirmationDialog = AlertDialog.Builder(requireContext())
            .setTitle("Remove Member")
            .setMessage("Do you want to remove this member from the group?")
            .setPositiveButton("Remove") { dialog, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    groupMemberDao.removeUserFromGroup(groupId, userId)
                    refreshMemberList(groupId)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        confirmationDialog.show()
    }

    private suspend fun refreshMemberList(groupId: Long) {
        val userIds = groupMemberDao.getUserIdsForGroup(groupId)
        val users = userRepository.getUsersByIds(userIds)
        memberAdapter.submitList(users)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
