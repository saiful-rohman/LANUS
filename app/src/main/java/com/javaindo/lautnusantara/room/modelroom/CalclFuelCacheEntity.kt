package com.javaindo.lautnusantara.room.modelroom

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "calculateFuel")
data class CalclFuelCacheEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @NonNull
    var id : Int,
    @ColumnInfo(name = "bbmConsume")
    var bbmConsume : String,
    @ColumnInfo(name = "mileage")
    var mileage : String,
    @ColumnInfo(name = "speed")
    var speed : String,
    @ColumnInfo(name = "brandEngine")
    var brandEngine : String,
    @ColumnInfo(name = "engine")
    var engine : String

){

}