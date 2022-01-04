package com.javaindo.lautnusantara.repository

import androidx.lifecycle.LiveData
import com.javaindo.lautnusantara.model.LatLongModel
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.retrofit.mapperretrofit.LatLongNetworkMapper
import com.javaindo.lautnusantara.room.dao.LatLongDao
import com.javaindo.lautnusantara.room.mapperroom.LatLongCacheMapper
import com.javaindo.lautnusantara.utility.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

class LatLongRepository constructor(
    private val latLongDao: LatLongDao,
    private val api: API,
    private val latLongCacheMapper: LatLongCacheMapper,
    private val latLongNetworkMapper: LatLongNetworkMapper
) {

    fun getLatLongData() : Flow<DataState<List<LatLongModel>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
//            val network = api.getLatLongData()
//            val datas = latLongNetworkMapper.mapFromEntityList(network)
            val datas = generateDummy()
            datas.forEach {
                latLongDao.insert(latLongCacheMapper.mapToEntity(it))
            }
            val cacheDatas = latLongDao.getLatLongList()
            emit(DataState.Success(latLongCacheMapper.mapFromEntityList(cacheDatas)))
        }catch (e : Exception){
            emit(DataState.Error(e))
        }

    }

    private fun generateDummy() : ArrayList<LatLongModel>{
        var resultData : ArrayList<LatLongModel> = ArrayList<LatLongModel>()

        for(i in 0..15){
            val ll = if (i>10) "0${i}" else "${i}"
            val dataLatLong = LatLongModel(
                i,
                "0${ll},00",
                "0${ll},00",
                "0${ll},00",
                "0${ll},00",
                "0${ll},00",
                "0${ll},00",
                "0${ll},00",
                "0${ll},00",
                "0${ll},00"
            )

            resultData.add(dataLatLong)
        }

        return resultData
    }

}