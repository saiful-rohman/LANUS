package com.javaindo.lautnusantara.retrofit.modelretrofit

import com.google.gson.annotations.SerializedName

data class LatLongNetworkEntity(
    @SerializedName("indx")
    var indx : Int,
    @SerializedName("longitude")
    var longitude : String,
    @SerializedName("longitudeD")
    var longitudeD : String,
    @SerializedName("longitudeM")
    var longitudeM : String,
    @SerializedName("longitudeS")
    var longitudeS : String,
    @SerializedName("latitude")
    var latitude : String,
    @SerializedName("latitudeD")
    var latitudeD : String,
    @SerializedName("latitudeM")
    var latitudeM : String,
    @SerializedName("latitudeS")
    var latitudeS : String,
    @SerializedName("longlatDate")
    var longlatDate : String
)
