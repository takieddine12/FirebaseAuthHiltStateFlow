package com.example.firebaselivedatahilt.ui.mvvm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UserViewModel @ViewModelInject constructor (var repository: UserRepository) : ViewModel() {

    private val _flowStates : MutableStateFlow<UiStates> = MutableStateFlow(UiStates.EmptyState)
      val uiStates  : StateFlow<UiStates> = _flowStates

    private var mutableLiveData : MutableLiveData<Boolean>? = MutableLiveData()


    //LiveData
    fun signUpUser(email : String , password : String )  {
        viewModelScope.launch {
            try {
                _flowStates.value = UiStates.Loading
                repository.getUserCreds().createUserWithEmailAndPassword(email,password).await()
                _flowStates.value = UiStates.Success
            }catch ( ex : Exception){
                _flowStates.value = UiStates.Error(ex.toString())
            }

        }

    }
    fun signInUser(email: String, password: String)  {
        viewModelScope.launch {
           try {
               _flowStates.value = UiStates.Loading
               repository.getUserCreds().signInWithEmailAndPassword(email,password).await()
               _flowStates.value = UiStates.Success
           }catch (ex : Exception){
               _flowStates.value = UiStates.Error(ex.toString())

           }
        }
    }


    sealed class UiStates{
        object Loading : UiStates()
        object Success : UiStates()
        data class Error( var exception : String) : UiStates()
        object EmptyState : UiStates()
    }

}