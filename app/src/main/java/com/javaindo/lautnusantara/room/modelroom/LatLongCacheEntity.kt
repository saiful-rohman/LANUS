package com.javaindo.lautnusantara.room.modelroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "latlong")
data class LatLongCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "indx")
    var indx: Int,
    @ColumnInfo(name = "longitude")
    var longitude : String,
    @ColumnInfo(name = "longitudeD")
    var longitudeD : String,
    @ColumnInfo(name = "longitudeM")
    var longitudeM : String,
    @ColumnInfo(name = "longitudeS")
    var longitudeS : String,
    @ColumnInfo(name = "latitude")
    var latitude : String,
    @ColumnInfo(name = "latitudeD")
    var latitudeD : String,
    @ColumnInfo(name = "latitudeM")
    var latitudeM : String,
    @ColumnInfo(name = "latitudeS")
    var latitudeS : String,
    @ColumnInfo(name = "longlatDate")
    var longlatDate : String
)
