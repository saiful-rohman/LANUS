package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javaindo.lautnusantara.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo : LoginRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    fun processData(){
        viewModelScope.launch {
            repo.processData()
        }
    }

}