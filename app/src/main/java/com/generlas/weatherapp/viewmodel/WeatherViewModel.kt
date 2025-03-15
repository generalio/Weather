package com.generlas.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generlas.weatherapp.model.bean.DailyResponse
import com.generlas.weatherapp.model.bean.Realtime
import com.generlas.weatherapp.model.network.WeatherNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.math.ln

/**
 * description ： TODO:类的作用
 * date : 2025/3/14 17:19
 */
class WeatherViewModel :ViewModel() {

    val realtimeLiveData = MutableLiveData<Realtime>()
    val dailyLiveData = MutableLiveData<DailyResponse.Daily>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    private val compositeDisposable = CompositeDisposable()

    fun getRealtime(lng: String, lat: String) {
        val disposable = WeatherNetwork.getRealtimeWeather(lng, lat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { realtimeResponse ->
                realtimeLiveData.postValue(realtimeResponse.result.realtime)
            }
            .doOnError {error ->
                Log.d("zzx",error.stackTraceToString());
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun getDaily(lng: String, lat: String) {
        val disposable = WeatherNetwork.getDailyWeather(lng, lat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { dailyResponse ->
                dailyLiveData.postValue(dailyResponse.result.daily)
            }
            .doOnError { error ->
                Log.d("zzx",error.stackTraceToString());
            }
            .subscribe()

        compositeDisposable.add(disposable)
    }
}