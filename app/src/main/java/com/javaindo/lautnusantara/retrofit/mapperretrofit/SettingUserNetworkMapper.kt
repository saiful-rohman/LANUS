package com.javaindo.lautnusantara.retrofit.mapperretrofit

import com.javaindo.lautnusantara.model.SettingUserModel
import com.javaindo.lautnusantara.retrofit.modelretrofit.SettingUserNetworkEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject

class SettingUserNetworkMapper @Inject constructor() : EntityMapper<SettingUserNetworkEntity, SettingUserModel>{
    override fun mapFromEntity(entity: SettingUserNetworkEntity): SettingUserModel {
        return SettingUserModel(
            id = entity.id,
            bbmConsume = entity.bbmConsume,
            mileage = entity.mileage,
            speed = entity.speed,
            brandEngine = entity.brandEngine,
            engine = entity.engine
        )
    }

    override fun mapToEntity(domailModel: SettingUserModel): SettingUserNetworkEntity {
        return SettingUserNetworkEntity(
            id = domailModel.id,
            bbmConsume = domailModel.bbmConsume,
            mileage = domailModel.mileage,
            speed = domailModel.speed,
            brandEngine = domailModel.brandEngine,
            engine = domailModel.engine
        )
    }

    fun mapFromEntityList(entities : List<SettingUserNetworkEntity>) : List<SettingUserModel>{
        return entities.map { mapFromEntity(it) }
    }
}