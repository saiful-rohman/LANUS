package com.javaindo.lautnusantara.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javaindo.lautnusantara.room.modelroom.DatakuCacheEntity

@Dao
interface DatakuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataku(entity: DatakuCacheEntity): Long

    @Query("SELECT * FROM dataku")
    suspend fun getAllDataku() : List<DatakuCacheEntity>

}