package com.javaindo.lautnusantara.repository

import android.util.Log
import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.retrofit.mapperretrofit.SettingUserNetworkMapper
import com.javaindo.lautnusantara.room.dao.SettingUserDao
import com.javaindo.lautnusantara.room.mapperroom.SettingUserCacheMapper
import com.javaindo.lautnusantara.utility.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SettingUserRepository constructor(
    private val settingUserDao: SettingUserDao,
    private val api: API,
    private val cacheCacheMapper : SettingUserCacheMapper,
    private val networkMapper : SettingUserNetworkMapper
){

    suspend fun getUsetSettings() : Flow<DataState<SettingUserModel>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            val dataCache = settingUserDao.getSetting()
            emit(DataState.Success(cacheCacheMapper.mapFromEntity(dataCache)))
        } catch (e : Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun saveSettings(data : SettingUserModel){
        try{
            settingUserDao.insertSettingUser(cacheCacheMapper.mapToEntity(data))
        }catch (e : Exception){
            Log.d("error","${e.message}")
        }
    }

}