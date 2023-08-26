package com.example.messager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.NewCreationModel
import com.example.kotlin.StartModel
import com.example.messager.Adapter.StartAdapter
import com.example.messager.Database.AppDatabase
import com.example.messager.Database.Dao.GroupDao
import com.example.messager.Database.Dao.UserDao
import com.example.messager.Database.Entities.Group
import com.example.messager.Model.StartItem
import com.example.messager.databinding.FragmentStartBinding
import kotlinx.coroutines.launch

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var groupDao: GroupDao
    private lateinit var adapter: StartAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupDao = AppDatabase.getInstance(requireContext()).groupDao()

        adapter = StartAdapter(emptyList()) // Initialize the adapter with an empty list
        binding.recyclerViewStartChat.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewStartChat.adapter = adapter

        lifecycleScope.launch {
            val groups = groupDao.getAllGroups()
            adapter.updateData(groups)
        }

        // Floating action button
        binding.extendedFabStartChat.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_NewCreationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}



