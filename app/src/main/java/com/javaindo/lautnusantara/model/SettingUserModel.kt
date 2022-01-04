package com.javaindo.lautnusantara.model

import java.io.Serializable

data class SettingUserModel(
    val id : Int = 0,
    val bbmConsume : String = "",
    val mileage : String = "",
    val speed : String = "",
    val brandEngine : String = "",
    val engine : String = ""
)
