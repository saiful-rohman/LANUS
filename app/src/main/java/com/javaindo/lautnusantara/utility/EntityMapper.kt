package com.javaindo.lautnusantara.utility

interface EntityMapper<Entity, DomailModel> {

    fun mapFromEntity(entity: Entity) : DomailModel
    fun mapToEntity(domailModel: DomailModel) : Entity
}