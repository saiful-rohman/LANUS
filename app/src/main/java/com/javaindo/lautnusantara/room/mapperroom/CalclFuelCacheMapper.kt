package com.javaindo.lautnusantara.room.mapperroom

import com.javaindo.lautnusantara.model.CalculateFuelModel
import com.javaindo.lautnusantara.room.modelroom.CalclFuelCacheEntity
import com.javaindo.lautnusantara.utility.EntityMapper
import javax.inject.Inject


class CalclFuelCacheMapper @Inject constructor() : EntityMapper<CalclFuelCacheEntity, CalculateFuelModel> {
    override fun mapFromEntity(entity: CalclFuelCacheEntity): CalculateFuelModel {
        return CalculateFuelModel(
            bbmConsume = entity.bbmConsume,
            mileage = entity.mileage,
            speed = entity.speed,
            brandEngine = entity.brandEngine,
            engine = entity.engine
        )
    }

    override fun mapToEntity(domailModel: CalculateFuelModel): CalclFuelCacheEntity {
        return CalclFuelCacheEntity(
            id = domailModel.id,
            bbmConsume = domailModel.bbmConsume,
            mileage = domailModel.mileage,
            speed = domailModel.speed,
            brandEngine = domailModel.brandEngine,
            engine = domailModel.engine
        )
    }

    fun mapFromEntityList(entities : List<CalclFuelCacheEntity>) : List<CalculateFuelModel>{
        return entities.map { mapFromEntity(it) }
    }

}