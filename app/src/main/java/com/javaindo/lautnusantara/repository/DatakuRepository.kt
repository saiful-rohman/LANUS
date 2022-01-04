package com.javaindo.lautnusantara.repository

import com.javaindo.lautnusantara.model.DatakuModel
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.retrofit.mapperretrofit.DatakuNetworkMapper
import com.javaindo.lautnusantara.room.dao.DatakuDao
import com.javaindo.lautnusantara.room.mapperroom.DatakuCacheMapper
import com.javaindo.lautnusantara.utility.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception


class DatakuRepository constructor(
    private val datakuDao: DatakuDao,
    private val api: API,
    private val cacheMapper: DatakuCacheMapper,
    private val networkMapper: DatakuNetworkMapper
){

    suspend fun getDataku(): Flow<DataState<List<DatakuModel>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkApi = api.getDataku()
            val datas = networkMapper.mapFromEntityList(networkApi)
            for(data in datas){
                datakuDao.insertDataku(cacheMapper.mapToEntity(data))
            }
            val cacheDatakus = datakuDao.getAllDataku()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cacheDatakus)))
        }catch (e : Exception){
            emit(DataState.Error(e))
        }
    }

}