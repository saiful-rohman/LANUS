package com.javaindo.lautnusantara.room.mapperroom

import com.javaindo.lautnusantara.model.OnlineUserModel
import com.javaindo.lautnusantara.room.modelroom.OnlineUserCacheEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject

class OnlineUserCacheMapper @Inject constructor() : EntityMapper< OnlineUserCacheEntity, OnlineUserModel> {

    override fun mapFromEntity(entity: OnlineUserCacheEntity): OnlineUserModel {
        return OnlineUserModel(
            index = entity.index,
            amountOnline = entity.amountOnline
        )
    }

    override fun mapToEntity(domailModel: OnlineUserModel): OnlineUserCacheEntity {
        return OnlineUserCacheEntity(
            index = domailModel.index,
            amountOnline = domailModel.amountOnline
        )
    }
}