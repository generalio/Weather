package com.generlas.weatherapp.model.network

import com.generlas.weatherapp.model.bean.PlaceResponse
import com.generlas.weatherapp.utils.ServiceCreator
import io.reactivex.rxjava3.core.Observable

/**
 * description ： 数据源封装
 * date : 2025/3/13 14:34
 */
object WeatherNetwork {

    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    fun searchPlaces(query: String): Observable<PlaceResponse> = placeService.searchPlaces(query)

    fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat)
    fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat)

}