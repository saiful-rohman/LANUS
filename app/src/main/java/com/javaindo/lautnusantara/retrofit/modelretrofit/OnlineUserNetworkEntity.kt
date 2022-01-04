package com.javaindo.lautnusantara.retrofit.modelretrofit

import com.google.gson.annotations.SerializedName

data class OnlineUserNetworkEntity(
    @SerializedName("index")
    var index: Int,
    @SerializedName("amountOnline")
    var amountOnline: Int

) {
}