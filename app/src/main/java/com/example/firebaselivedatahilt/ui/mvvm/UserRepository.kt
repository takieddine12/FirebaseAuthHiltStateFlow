package com.example.firebaselivedatahilt.ui.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRepository @Inject constructor( private var  firebaseAuth: FirebaseAuth)

{

    fun getUserCreds() : FirebaseAuth {
        return firebaseAuth

    }
}

