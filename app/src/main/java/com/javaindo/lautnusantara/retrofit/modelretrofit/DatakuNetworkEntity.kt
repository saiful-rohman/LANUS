package com.javaindo.lautnusantara.retrofit.modelretrofit

import com.google.gson.annotations.SerializedName

data class DatakuNetworkEntity(
    @SerializedName("pk")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("category")
    var category: String

) {
}