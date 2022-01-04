package com.javaindo.lautnusantara.repository

import com.javaindo.lautnusantara.model.OnlineUserModel
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.retrofit.mapperretrofit.OnlineUserNetworkMapper
import com.javaindo.lautnusantara.retrofit.modelretrofit.OnlineUserNetworkEntity
import com.javaindo.lautnusantara.room.dao.OnlineUserDao
import com.javaindo.lautnusantara.room.mapperroom.OnlineUserCacheMapper
import com.javaindo.lautnusantara.utility.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class HomeRepository constructor(
    private val onlineUserDao: OnlineUserDao,
    private val api: API,
    private val onlineUserCacheMapper: OnlineUserCacheMapper,
    private val onlineUserNetworkMapper: OnlineUserNetworkMapper
){

    suspend fun getDataOnlineUser() : Flow<DataState<OnlineUserModel>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            val network = api.getOnlineUser()
            val data = onlineUserNetworkMapper.mapFromEntity(network)
            onlineUserDao.insertOnlineUser(onlineUserCacheMapper.mapToEntity(data))
            val cacheData = onlineUserDao.getOnlineUser()
            emit(DataState.Success(onlineUserCacheMapper.mapFromEntity(cacheData)))
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

}