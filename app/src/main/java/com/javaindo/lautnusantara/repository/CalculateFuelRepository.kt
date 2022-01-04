package com.javaindo.lautnusantara.repository

import android.util.Log
import com.javaindo.lautnusantara.model.CalculateFuelModel
import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.retrofit.mapperretrofit.CalclFuelNetworkMapper
import com.javaindo.lautnusantara.room.dao.CalclFuelDao
import com.javaindo.lautnusantara.room.dao.SettingUserDao
import com.javaindo.lautnusantara.room.mapperroom.CalclFuelCacheMapper
import com.javaindo.lautnusantara.room.mapperroom.SettingUserCacheMapper
import com.javaindo.lautnusantara.utility.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception


class CalculateFuelRepository constructor(
    private val calclFuelDao: CalclFuelDao,
    private val settingUserDao: SettingUserDao,
    private val api: API,
    private val calclFuelCacheMapper: CalclFuelCacheMapper,
    private val calclFuelNetworkMapper: CalclFuelNetworkMapper,
    private val settingCacheMapper: SettingUserCacheMapper,
) {

    suspend fun getUsetSettings() : Flow<DataState<SettingUserModel>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            val dataCache = settingUserDao.getSetting()
            emit(DataState.Success(settingCacheMapper.mapFromEntity(dataCache)))
        } catch (e : Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun saveCalculateFuel(data : CalculateFuelModel){
        try{
            calclFuelDao.insertSettingUser(calclFuelCacheMapper.mapToEntity(data))
        }catch (e : Exception){
            Log.d("error","${e.message}")
        }
    }
    
}