package com.javaindo.lautnusantara.repository

import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.retrofit.mapperretrofit.SettingUserNetworkMapper
import com.javaindo.lautnusantara.room.dao.SettingUserDao
import com.javaindo.lautnusantara.room.mapperroom.SettingUserCacheMapper


class LoginRepository constructor(
    private val settingUserDao: SettingUserDao,
    private val api: API,
    private val cacheCacheMapper : SettingUserCacheMapper,
    private val networkMapper : SettingUserNetworkMapper
){

    suspend fun processData(){
        val dataSetting = SettingUserModel(
            1,
            "GALON",
            "KM",
            "KNOT",
            "MERCURY",
            "4 Stroke - 3.5 HP"
        )
        val dataInsert = cacheCacheMapper.mapToEntity(dataSetting)
        settingUserDao.insertSettingUser(dataInsert)


    }

}