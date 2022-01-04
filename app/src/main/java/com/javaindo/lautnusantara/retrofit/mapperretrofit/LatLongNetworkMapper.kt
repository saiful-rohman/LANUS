package com.javaindo.lautnusantara.retrofit.mapperretrofit

import com.javaindo.lautnusantara.model.LatLongModel
import com.javaindo.lautnusantara.retrofit.modelretrofit.LatLongNetworkEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject

class LatLongNetworkMapper @Inject constructor() : EntityMapper<LatLongNetworkEntity, LatLongModel> {
    override fun mapFromEntity(entity: LatLongNetworkEntity): LatLongModel {
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

    override fun mapToEntity(domailModel: LatLongModel): LatLongNetworkEntity {
        return LatLongNetworkEntity(
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

    fun mapFromEntityList(datas : List<LatLongNetworkEntity>) : List<LatLongModel>{
        return datas.map { mapFromEntity(it)}
    }


}