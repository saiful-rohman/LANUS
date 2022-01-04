package com.javaindo.lautnusantara.room.modelroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "onlineuser")
data class OnlineUserCacheEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "index")
    var index : Int,
    @ColumnInfo(name = "amountOnline")
    var amountOnline : Int

)
