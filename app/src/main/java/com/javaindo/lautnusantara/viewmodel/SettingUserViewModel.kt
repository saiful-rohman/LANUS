package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.*
import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.repository.SettingUserRepository
import com.javaindo.lautnusantara.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingUserViewModel @Inject constructor(
    private val repo : SettingUserRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    val _settingValues : MutableLiveData<DataState<SettingUserModel>> = MutableLiveData()
    val settingValues : LiveData<DataState<SettingUserModel>> get() = _settingValues

    fun getSettingUser(){
        viewModelScope.launch {
            repo.getUsetSettings().onEach { data ->
                _settingValues.value = data
            }.launchIn(viewModelScope)
        }
    }

    fun saveSetting(data : SettingUserModel){
        viewModelScope.launch {
            repo.saveSettings(data)
        }
    }

}