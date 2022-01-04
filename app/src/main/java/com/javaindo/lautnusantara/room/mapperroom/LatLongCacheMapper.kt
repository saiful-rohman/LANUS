package com.javaindo.lautnusantara.room.mapperroom

import com.javaindo.lautnusantara.model.LatLongModel
import com.javaindo.lautnusantara.room.modelroom.LatLongCacheEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject

class LatLongCacheMapper @Inject constructor() : EntityMapper<LatLongCacheEntity, LatLongModel>{
    override fun mapFromEntity(entity: LatLongCacheEntity): LatLongModel {
        return LatLongModel(
            indx = entity.indx,
            longitude = entity.longitude,
            longitudeD = entity.longitudeD,
            longitudeM = entity.longitudeM,
            longitudeS = entity.longitudeS,
            latitude = entity.latitude,
            latitudeD = entity.latitudeD,
            latitudeM = entity.latitudeM,
            latitudeS = entity.latitudeS,
            longlatDate = entity.longlatDate
        )
    }

    override fun mapToEntity(domailModel: LatLongModel): LatLongCacheEntity {
        return LatLongCacheEntity(
            indx = domailModel.indx,
            longitude = domailModel.longitude,
            longitudeD = domailModel.longitudeD,
            longitudeM = domailModel.longitudeM,
            longitudeS = domailModel.longitudeS,
            latitude = domailModel.latitude,
            latitudeD = domailModel.latitudeD,
            latitudeM = domailModel.latitudeM,
            latitudeS = domailModel.latitudeS,
            longlatDate = domailModel.longlatDate
        )
    }

    fun mapFromEntityList(datas : List<LatLongCacheEntity>) : List<LatLongModel>{
        return datas.map { mapFromEntity(it) }
    }
}