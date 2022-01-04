package com.javaindo.lautnusantara.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javaindo.lautnusantara.room.modelroom.OnlineUserCacheEntity

@Dao
interface OnlineUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnlineUser(entity : OnlineUserCacheEntity): Long

    @Query("SELECT * FROM onlineuser")
    suspend fun getOnlineUser() : OnlineUserCacheEntity
}