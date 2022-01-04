package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.*
import com.javaindo.lautnusantara.model.OnlineUserModel
import com.javaindo.lautnusantara.repository.HomeRepository
import com.javaindo.lautnusantara.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class HomeViewModel : ViewModel() {
//@Inject constructor(
//    private val homeRepository: HomeRepository,
//    private val savedStateHandle: SavedStateHandle
//) : ViewModel() {

//    private var _onlineUser : MutableLiveData<DataState<OnlineUserModel>> = MutableLiveData()
//    val onlineuser : LiveData<DataState<OnlineUserModel>> get() = _onlineUser
//
//    fun setHomeStateEvent(homeStateEvent: HomeStateEvent){
//        viewModelScope.launch {
//            when(homeStateEvent){
//                is HomeStateEvent.getUserOnline -> {
////                    homeRepository.getDataOnlineUser().fl
//                }
//            }
//        }
//    }

}

//sealed class HomeStateEvent{
//    object getUserOnline : HomeStateEvent()
//    object None : HomeStateEvent()
//}