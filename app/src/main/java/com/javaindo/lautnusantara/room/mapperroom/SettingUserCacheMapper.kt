package com.javaindo.lautnusantara.room.mapperroom

import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.room.modelroom.SettingUserCacheEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject


class SettingUserCacheMapper @Inject constructor() : EntityMapper<SettingUserCacheEntity, SettingUserModel> {
    override fun mapFromEntity(entity: SettingUserCacheEntity): SettingUserModel {
        return SettingUserModel(
            bbmConsume = entity.bbmConsume,
            mileage = entity.mileage,
            speed = entity.speed,
            brandEngine = entity.brandEngine,
            engine = entity.engine
        )
    }

    override fun mapToEntity(domailModel: SettingUserModel): SettingUserCacheEntity {
        return SettingUserCacheEntity(
            id = domailModel.id,
            bbmConsume = domailModel.bbmConsume,
            mileage = domailModel.mileage,
            speed = domailModel.speed,
            brandEngine = domailModel.brandEngine,
            engine = domailModel.engine
        )
    }

    fun mapFromEntityList(entities : List<SettingUserCacheEntity>) : List<SettingUserModel>{
        return entities.map { mapFromEntity(it) }
    }

}