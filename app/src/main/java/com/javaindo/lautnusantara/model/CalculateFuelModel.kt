package com.javaindo.lautnusantara.model

import java.io.Serializable

data class CalculateFuelModel(
    val id : Int = 0,
    val bbmConsume : String = "",
    val mileage : String = "",
    val speed : String = "",
    val brandEngine : String = "",
    val engine : String = ""
)
