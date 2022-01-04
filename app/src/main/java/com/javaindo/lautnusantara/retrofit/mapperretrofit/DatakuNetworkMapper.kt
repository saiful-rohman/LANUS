package com.javaindo.lautnusantara.retrofit.mapperretrofit

import com.javaindo.lautnusantara.model.DatakuModel
import com.javaindo.lautnusantara.retrofit.modelretrofit.DatakuNetworkEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject

class DatakuNetworkMapper @Inject constructor() : EntityMapper<DatakuNetworkEntity, DatakuModel>{
    override fun mapFromEntity(entity: DatakuNetworkEntity): DatakuModel {
        return DatakuModel(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            image = entity.image,
            category = entity.category
        )
    }

    override fun mapToEntity(domailModel: DatakuModel): DatakuNetworkEntity {
        return DatakuNetworkEntity(
            id = domailModel.id,
            title = domailModel.title,
            body = domailModel.body,
            image = domailModel.image,
            category = domailModel.category
        )
    }

    fun mapFromEntityList(entities : List<DatakuNetworkEntity>) : List<DatakuModel>{
        return entities.map { mapFromEntity(it) }
    }
}