package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.*
import com.javaindo.lautnusantara.model.ResultCatchModel
import com.javaindo.lautnusantara.model.SearchResultCatchParmModel
import com.javaindo.lautnusantara.repository.ResultCatchRepository
import com.javaindo.lautnusantara.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultCatchViewModel @Inject constructor(
    private val repo : ResultCatchRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _resultCatchData : MutableLiveData<DataState<ArrayList<ResultCatchModel>>> = MutableLiveData()
    val resultCatchData : LiveData<DataState<ArrayList<ResultCatchModel>>> get() = _resultCatchData

    fun getResultCacth(resultStateValue: ResultStateValue, searchParm: SearchResultCatchParmModel) {
        viewModelScope.launch {
            when(resultStateValue){
                is ResultStateValue.getResultCatch -> {
                    repo.getResultCatch(searchParm).onEach { datas ->
                        _resultCatchData.value = datas
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

}

sealed class ResultStateValue{
    object getResultCatch : ResultStateValue()
}