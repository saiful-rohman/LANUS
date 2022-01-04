package com.javaindo.lautnusantara.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.javaindo.lautnusantara.model.OnlineUserModel
import com.javaindo.lautnusantara.repository.HomeRepository
import com.javaindo.lautnusantara.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel2 @Inject constructor(
    private val homeRepository: HomeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _onlineUser : MutableLiveData<DataState<OnlineUserModel>> = MutableLiveData()
    val onlineuser : LiveData<DataState<OnlineUserModel>> get() = _onlineUser

    fun setHomeStateEvent2(homeStateEvent: HomeStateEvent2){
        Log.d("bbbb","bbbb")
        viewModelScope.launch {
            when(homeStateEvent){
                is HomeStateEvent2.getUserOnline -> {
                    homeRepository.getDataOnlineUser().onEach { dataState ->
                        _onlineUser.value = dataState
                    }
                }
                is HomeStateEvent2.None -> {

                }
            }
        }
    }

}

sealed class HomeStateEvent2{
    object getUserOnline : HomeStateEvent2()
    object None : HomeStateEvent2()
}
//class HomeViewModel2 : ViewModel(){
//
//}