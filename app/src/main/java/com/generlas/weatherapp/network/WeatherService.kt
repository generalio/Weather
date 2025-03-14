package com.generlas.weatherapp.network

import com.generlas.weatherapp.bean.DailyResponse
import com.generlas.weatherapp.bean.RealtimeResponse
import com.generlas.weatherapp.utils.WeatherApplication
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * description ： TODO:类的作用
 * date : 2025/3/14 16:58
 */
interface WeatherService {

    @GET("v2.6/${WeatherApplication.TOKEN}/{lng},{lat}/realtime")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Observable<RealtimeResponse>

    @GET("v2.6/${WeatherApplication.TOKEN}/{lng},{lat}/daily")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Observable<DailyResponse>
}