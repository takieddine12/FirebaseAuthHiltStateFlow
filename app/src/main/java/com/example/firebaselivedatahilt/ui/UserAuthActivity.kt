package com.example.firebaselivedatahilt.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.firebaselivedatahilt.R
import com.example.firebaselivedatahilt.UserLoginFragment
import com.example.firebaselivedatahilt.databinding.ActivityUserAuthBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAuthActivity : AppCompatActivity() {
    private val _binding : ActivityUserAuthBinding? = null
    private var binding = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserAuthBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
         navHostFragment.navController


    }
}