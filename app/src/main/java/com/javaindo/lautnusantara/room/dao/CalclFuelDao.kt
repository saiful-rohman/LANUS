package com.javaindo.lautnusantara.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javaindo.lautnusantara.room.modelroom.CalclFuelCacheEntity
import com.javaindo.lautnusantara.room.modelroom.DatakuCacheEntity
import com.javaindo.lautnusantara.room.modelroom.SettingUserCacheEntity

@Dao
interface CalclFuelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettingUser(entity: CalclFuelCacheEntity): Long

    @Query("SELECT * FROM calculateFuel")
    suspend fun getCalculateFuelAll() : List<CalclFuelCacheEntity>

    @Query("SELECT * FROM calculateFuel")
    suspend fun getCalculateFuel() : CalclFuelCacheEntity

    @Query("SELECT * FROM settingUser")
    suspend fun getSettingUser() : SettingUserCacheEntity

}