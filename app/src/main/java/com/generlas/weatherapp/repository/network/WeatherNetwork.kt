package com.generlas.weatherapp.repository.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.generlas.weatherapp.repository.model.Place
import com.generlas.weatherapp.utils.ServiceCreator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： 数据源封装
 * date : 2025/3/13 14:34
 */
object WeatherNetwork {

    private val placeService = ServiceCreator.create<PlaceService>()
    private val compositeDisposable = CompositeDisposable()

    fun searchPlaces(query: String): LiveData<List<Place>> {

        val liveData = MutableLiveData<List<Place>>()

        val disposable = placeService.searchPlaces(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ placeResponse ->
                if(placeResponse.status != "failed") {
                    liveData.value = placeResponse.places
                }
            }, { error ->
                Log.d("zzx", "(${error.message}:)-->>")
            })

        compositeDisposable.add(disposable)

        return liveData
    }

    // 清理资源的方法
    fun clearDisposable() {
        compositeDisposable.clear()
    }
}