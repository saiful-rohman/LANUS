package com.javaindo.lautnusantara.room.mapperroom

import com.javaindo.lautnusantara.model.DatakuModel
import com.javaindo.lautnusantara.room.modelroom.DatakuCacheEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject


class DatakuCacheMapper @Inject constructor() : EntityMapper<DatakuCacheEntity, DatakuModel> {
    override fun mapFromEntity(entity: DatakuCacheEntity): DatakuModel {
        return DatakuModel(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            image = entity.image,
            category = entity.category
        )
    }

    override fun mapToEntity(domailModel: DatakuModel): DatakuCacheEntity {
        return DatakuCacheEntity(
            id = domailModel.id,
            title = domailModel.title,
            body = domailModel.body,
            image = domailModel.image,
            category = domailModel.category
        )
    }

    fun mapFromEntityList(entities : List<DatakuCacheEntity>) : List<DatakuModel>{
        return entities.map { mapFromEntity(it) }
    }

}