package com.generlas.weatherapp.network

import com.generlas.weatherapp.bean.PlaceResponse
import com.generlas.weatherapp.utils.ServiceCreator
import io.reactivex.rxjava3.core.Observable

/**
 * description ： 数据源封装
 * date : 2025/3/13 14:34
 */
object WeatherNetwork {

    val placeService = ServiceCreator.create<PlaceService>()

    fun searchPlaces(query: String): Observable<PlaceResponse> = placeService.searchPlaces(query)

}