package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.*
import com.javaindo.lautnusantara.model.CalculateFuelModel
import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.repository.CalculateFuelRepository
import com.javaindo.lautnusantara.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculateFuelViewModel @Inject constructor(
    private val repo : CalculateFuelRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    val _settingUserValues : MutableLiveData<DataState<SettingUserModel>> = MutableLiveData()
    val settingUserValues : LiveData<DataState<SettingUserModel>> get() = _settingUserValues

    fun getSettingUser(){
        viewModelScope.launch {
            repo.getUsetSettings().onEach { data ->
                _settingUserValues.value = data
            }.launchIn(viewModelScope)
        }
    }

    fun saveCalculateFuel(data : CalculateFuelModel){
        viewModelScope.launch {
            repo.saveCalculateFuel(data)
        }
    }
    
}