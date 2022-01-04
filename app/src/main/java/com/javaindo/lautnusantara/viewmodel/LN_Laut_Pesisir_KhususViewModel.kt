package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.*
import com.javaindo.lautnusantara.model.LatLongModel
import com.javaindo.lautnusantara.repository.LatLongRepository
import com.javaindo.lautnusantara.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class LN_Laut_Pesisir_KhususViewModel @Inject constructor(
    private val latLongRepository: LatLongRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _latLongList :MutableLiveData<DataState<List<LatLongModel>>> = MutableLiveData()
    val latLongList : LiveData<DataState<List<LatLongModel>>> get() = _latLongList

    fun setStateEvent(latLongStateEvent: LatLongStateEvent){
        when(latLongStateEvent){
            is LatLongStateEvent.GetLatLong -> {
                latLongRepository.getLatLongData().onEach { dataState ->
                    _latLongList.value = dataState
                }.launchIn(viewModelScope)
            }
        }
    }



}

sealed class LatLongStateEvent{
    object GetLatLong : LatLongStateEvent()
}