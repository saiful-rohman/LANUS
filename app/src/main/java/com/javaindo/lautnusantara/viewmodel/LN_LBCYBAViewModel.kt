package com.javaindo.lautnusantara.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.javaindo.lautnusantara.repository.LBCYBARepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
class LN_LBCYBAViewModel @Inject constructor(
    lbcybaRepository: LBCYBARepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){


}