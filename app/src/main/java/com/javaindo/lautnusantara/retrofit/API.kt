package com.javaindo.lautnusantara.retrofit

import com.javaindo.lautnusantara.retrofit.modelretrofit.DatakuNetworkEntity
import com.javaindo.lautnusantara.retrofit.modelretrofit.LatLongNetworkEntity
import com.javaindo.lautnusantara.retrofit.modelretrofit.OnlineUserNetworkEntity
import retrofit2.http.GET

interface API {

    @GET("blogs")
    suspend fun getDataku() : List<DatakuNetworkEntity>


    //===============================prepare API==============================================
    @GET("")
    suspend fun getOnlineUser() : OnlineUserNetworkEntity

    @GET("")
    suspend fun getLatLongData() : List<LatLongNetworkEntity>

}