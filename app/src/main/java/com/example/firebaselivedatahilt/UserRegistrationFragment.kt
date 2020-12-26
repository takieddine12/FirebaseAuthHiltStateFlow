package com.example.firebaselivedatahilt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebaselivedatahilt.R
import dagger.hilt.android.AndroidEntryPoint
import com.example.firebaselivedatahilt.databinding.RegistrationlayoutBinding
import com.example.firebaselivedatahilt.ui.mvvm.UserViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserRegistrationFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModels()
    private val _binding: RegistrationlayoutBinding? = null
    private var binding = _binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RegistrationlayoutBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.register?.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                if (validateEmail() and validatePassword()) {
                    userViewModel.signUpUser(
                        binding?.emailInput?.editText?.text.toString().trim(),
                        binding?.passwordInput?.editText?.text.toString().trim()
                    )

                    userViewModel.uiStates.collect {
                        when(it){
                            is UserViewModel.UiStates.Loading -> {
                                binding?.progress?.visibility  = View.VISIBLE
                            }
                            is UserViewModel.UiStates.Success -> {
                                binding?.progress?.visibility  = View.INVISIBLE
                                findNavController()
                                    .navigate(
                                        R.id.action_userRegistrationFragment_to_userLoginFragment
                                    )
                            }
                            is UserViewModel.UiStates.Error -> {
                                binding?.progress?.visibility  = View.INVISIBLE
                                Toast.makeText(requireContext(),"Something wrong happened",Toast.LENGTH_SHORT).show()
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
        binding?.account?.setOnClickListener {
            findNavController()
                .navigate(R.id.action_userRegistrationFragment_to_userLoginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
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