package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.*
import com.javaindo.lautnusantara.model.PortMapModel
import com.javaindo.lautnusantara.model.SearchSellingPriceParmModel
import com.javaindo.lautnusantara.repository.SellingPriceRepository
import com.javaindo.lautnusantara.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellingPriceViewModel
@Inject constructor(
    private val repo : SellingPriceRepository,
    private val setStateHandle: SavedStateHandle
) : ViewModel() {

    val _portMapList : MutableLiveData<DataState<ArrayList<PortMapModel>>> = MutableLiveData()
    val portMapList : LiveData<DataState<ArrayList<PortMapModel>>> get() = _portMapList

    fun getSellPriceData (sellingPriceStateEvent: SellingPriceStateEvent, searchParm : SearchSellingPriceParmModel){
        viewModelScope.launch {
            when(sellingPriceStateEvent){
                is SellingPriceStateEvent.GetSellPrice ->{
                    repo.getSellingPrice(searchParm).onEach { data ->
                        _portMapList.value = data
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

}

sealed class SellingPriceStateEvent {
    object GetSellPrice : SellingPriceStateEvent()
    object None : SellingPriceStateEvent()
}