package com.example.messager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.messager.Database.AppDatabase
import com.example.messager.Database.Entities.User
import com.example.messager.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.LoginToAccount.setOnClickListener {
            val username = binding.editTextEmail.text.toString()
            val phoneNumber = binding.editTextPassword.text.toString()

            if (username.isNotEmpty() && phoneNumber.isNotEmpty()) {
                val user = User(userName = username, phoneNumber = phoneNumber)
                insertUser(user)
                findNavController().navigate(R.id.action_loginFragment_to_startFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill in the details", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun insertUser(user: User) {
        val userDao = AppDatabase.getInstance(requireContext()).userDao()

        GlobalScope.launch(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }
}




