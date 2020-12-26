package com.example.firebaselivedatahilt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.firebaselivedatahilt.R
import com.example.firebaselivedatahilt.databinding.LoginlayoutBinding
import com.example.firebaselivedatahilt.ui.MainActivity
import com.example.firebaselivedatahilt.ui.mvvm.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserLoginFragment  : Fragment(){

     val userViewModel: UserViewModel by viewModels()
    private val _binding: LoginlayoutBinding? = null
    private var binding = _binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LoginlayoutBinding.inflate(inflater, container, false)
        return binding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.login?.setOnClickListener {
           lifecycleScope.launchWhenStarted {
               if (validateEmail() and validatePassword()) {
                   userViewModel.signInUser(
                       binding?.emailInput?.editText?.text.toString().trim(),
                       binding?.passwordInput?.editText?.text.toString().trim()
                   )
                   userViewModel.uiStates.collect {
                       when(it){
                           is UserViewModel.UiStates.Loading ->{
                               binding?.progress?.visibility  = View.VISIBLE
                           }
                           is UserViewModel.UiStates.Success -> {
                               delay(2000)
                               binding?.progress?.visibility  = View.INVISIBLE
                               Intent(requireContext(),MainActivity::class.java).apply {
                                   startActivity(this)
                               }
                           }
                           is UserViewModel.UiStates.Error -> {
                               binding?.progress?.visibility  = View.INVISIBLE
                               Toast.makeText(requireContext(),"Somethig wrong happened",Toast.LENGTH_SHORT).show()
                           }
                           else -> {}
                       }
                   }
               }
           }
        }

        binding?.noaccount?.setOnClickListener {

            findNavController()
                .navigate(R.id.action_userLoginFragment_to_userRegistrationFragment)
        }

    }


    private fun validateEmail(): Boolean {
        val email = binding?.emailInput?.editText?.text.toString().trim()
        return if (email.isEmpty()) {
            binding?.emailInput?.error = "Insert Something..."
            false
        } else {
            binding?.emailInput?.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        val password = binding?.passwordInput?.editText?.text.toString().trim()
        return if (password.isEmpty()) {
            binding?.passwordInput?.error = "Insert Something..."
            false
        } else {
            binding?.passwordInput?.error = null
            true
        }
    }
}