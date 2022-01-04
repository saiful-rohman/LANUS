package com.javaindo.lautnusantara.retrofit.mapperretrofit

import com.javaindo.lautnusantara.model.CalculateFuelModel
import com.javaindo.lautnusantara.retrofit.modelretrofit.CalclFuelNetworkEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject

class CalclFuelNetworkMapper @Inject constructor() : EntityMapper<CalclFuelNetworkEntity, CalculateFuelModel>{
    override fun mapFromEntity(entity: CalclFuelNetworkEntity): CalculateFuelModel {
        return CalculateFuelModel(
            id = entity.id,
            bbmConsume = entity.bbmConsume,
            mileage = entity.mileage,
            speed = entity.speed,
            brandEngine = entity.brandEngine,
            engine = entity.engine
        )
    }

    override fun mapToEntity(domailModel: CalculateFuelModel): CalclFuelNetworkEntity {
        return CalclFuelNetworkEntity(
            id = domailModel.id,
            bbmConsume = domailModel.bbmConsume,
            mileage = domailModel.mileage,
            speed = domailModel.speed,
            brandEngine = domailModel.brandEngine,
            engine = domailModel.engine
        )
    }

    fun mapFromEntityList(entities : List<CalclFuelNetworkEntity>) : List<CalculateFuelModel>{
        return entities.map { mapFromEntity(it) }
    }
}