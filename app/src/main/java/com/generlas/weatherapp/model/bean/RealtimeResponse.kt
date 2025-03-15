package com.generlas.weatherapp.model.bean

/**  
* description ： TODO:类的作用
* date : 2025/3/14 16:45 
*/
data class RealtimeResponse(
    val result: Result,
    val status: String,
)

data class Result(
    val primary: Int,
    val realtime: Realtime
)

data class Realtime(
    val air_quality: AirQuality,
    val skycon: String,
    val temperature: Double,
)

data class AirQuality(
    val aqi: Aqi,
)

data class Aqi(
    val chn: Int,
)