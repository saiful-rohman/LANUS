package com.javaindo.lautnusantara.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javaindo.lautnusantara.room.modelroom.DatakuCacheEntity
import com.javaindo.lautnusantara.room.modelroom.SettingUserCacheEntity

@Dao
interface SettingUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettingUser(entity: SettingUserCacheEntity): Long

    @Query("SELECT * FROM settingUser")
    suspend fun getSetiingAll() : List<SettingUserCacheEntity>

    @Query("SELECT * FROM settingUser")
    suspend fun getSetting() : SettingUserCacheEntity

}