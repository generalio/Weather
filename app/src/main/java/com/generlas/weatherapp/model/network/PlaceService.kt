package com.generlas.weatherapp.model.network

import com.generlas.weatherapp.utils.WeatherApplication
import com.generlas.weatherapp.model.bean.PlaceResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * description ： 搜索数据的接口
 * date : 2025/3/13 14:41
 */
interface PlaceService {

    @GET("v2/place?token=${WeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Observable<PlaceResponse>
}