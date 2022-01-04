package com.javaindo.lautnusantara.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javaindo.lautnusantara.room.dao.*
import com.javaindo.lautnusantara.room.modelroom.*

@Database(entities = [DatakuCacheEntity::class,OnlineUserCacheEntity::class,
                     LatLongCacheEntity::class,SettingUserCacheEntity::class,
                     CalclFuelCacheEntity::class],version = 2)
abstract class DBLautNunasantar : RoomDatabase(){
    companion object{
        val DATABASE_NAME = "coba_db"
    }

    abstract fun datakuDao() : DatakuDao
    abstract fun onlineUserDao(): OnlineUserDao
    abstract fun latLongDao() : LatLongDao
    abstract fun settingUserDao() : SettingUserDao
    abstract fun calculateFuelDao() : CalclFuelDao

}