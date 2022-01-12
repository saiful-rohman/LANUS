package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.*
import com.javaindo.lautnusantara.model.DatakuModel
import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.repository.DatakuRepository
import com.javaindo.lautnusantara.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val datakuRepository: DatakuRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    val _settingValues : MutableLiveData<DataState<SettingUserModel>> = MutableLiveData()
    val settingValues : LiveData<DataState<SettingUserModel>> get() = _settingValues

//    private val _dataState : MutableLiveData<DataState<List<DatakuModel>>> = MutableLiveData()
//    val dataState : LiveData<DataState<List<DatakuModel>>> get() = _dataState

//    fun setStateEvent(mainStateEvent: MainStateEvent){
//        viewModelScope.launch {
//            when(mainStateEvent){
//                is MainStateEvent.GetDatakuEvent -> {
//                    datakuRepository.getDataku().onEach { dataStt ->
//                        _dataState.value = dataStt
//                    }.launchIn(viewModelScope)
//                }
//                is MainStateEvent.None ->{
//
//                }
//            }
//        }
//    }

    fun getSettingUser(){
        viewModelScope.launch {
            datakuRepository.getUsetSettings().onEach { data ->
                _settingValues.value = data
            }.launchIn(viewModelScope)
        }
    }

}

//sealed class MainStateEvent{
//    object GetDatakuEvent : MainStateEvent()
//    object None: MainStateEvent()
//}