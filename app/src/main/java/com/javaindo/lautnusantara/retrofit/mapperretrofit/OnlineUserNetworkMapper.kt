package com.javaindo.lautnusantara.retrofit.mapperretrofit

import com.javaindo.lautnusantara.model.DatakuModel
import com.javaindo.lautnusantara.model.OnlineUserModel
import com.javaindo.lautnusantara.retrofit.modelretrofit.DatakuNetworkEntity
import com.javaindo.lautnusantara.retrofit.modelretrofit.OnlineUserNetworkEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject

class OnlineUserNetworkMapper @Inject constructor() : EntityMapper<OnlineUserNetworkEntity, OnlineUserModel>{
    override fun mapFromEntity(entity: OnlineUserNetworkEntity): OnlineUserModel {
        return OnlineUserModel(
            index = entity.index,
            amountOnline = entity.amountOnline
        )
    }

    override fun mapToEntity(domailModel: OnlineUserModel): OnlineUserNetworkEntity {
        return OnlineUserNetworkEntity(
            index = domailModel.index,
            amountOnline = domailModel.amountOnline
        )
    }

    fun mapFromEntityList(entities : List<OnlineUserNetworkEntity>) : List<OnlineUserModel>{
        return entities.map { mapFromEntity(it) }
    }
}