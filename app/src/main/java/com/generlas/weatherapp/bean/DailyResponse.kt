package com.generlas.weatherapp.bean

import com.google.gson.annotations.SerializedName
import java.sql.Date

/**
 * description ： TODO:类的作用
 * date : 2025/3/14 16:52
 */
data class DailyResponse(val status: String, val result: Result) {
    data class Result(val daily: Daily)

    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>, val life_index: LifeIndex)

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: String)

    data class LifeIndex(val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>, val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)

    data class LifeDescription(val desc: String)
}
