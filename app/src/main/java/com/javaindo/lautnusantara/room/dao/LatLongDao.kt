package com.javaindo.lautnusantara.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javaindo.lautnusantara.room.modelroom.LatLongCacheEntity
import com.javaindo.lautnusantara.room.modelroom.SettingUserCacheEntity

@Dao
interface LatLongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LatLongCacheEntity) : Long

    @Query("SELECT * FROM latlong")
    suspend fun getLatLongList() : List<LatLongCacheEntity>

    @Query("SELECT * FROM settingUser")
    suspend fun getSetting() : SettingUserCacheEntity

}