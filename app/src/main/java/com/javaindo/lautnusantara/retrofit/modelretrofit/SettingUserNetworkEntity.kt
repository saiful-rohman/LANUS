package com.javaindo.lautnusantara.retrofit.modelretrofit

import com.google.gson.annotations.SerializedName

data class SettingUserNetworkEntity(
    @SerializedName("id")
    var id: Int,
    @SerializedName("bbmConsume")
    var bbmConsume: String,
    @SerializedName("mileage")
    var mileage: String,
    @SerializedName("speed")
    var speed: String,
    @SerializedName("brandEngine")
    var brandEngine: String,
    @SerializedName("engine")
    var engine: String

) {
}