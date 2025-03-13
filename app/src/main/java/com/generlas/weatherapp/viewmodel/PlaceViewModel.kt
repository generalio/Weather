package com.generlas.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generlas.weatherapp.bean.Place
import com.generlas.weatherapp.network.WeatherNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： 搜索城市的viewmodel
 * date : 2025/3/13 14:39
 */
class PlaceViewModel : ViewModel() {

    var placeList = ArrayList<Place>()

    val placeLiveData: MutableLiveData<List<Place>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun searchPlaces(query: String) {

        val disposable = WeatherNetwork.searchPlaces(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ placeResponse ->
                placeLiveData.postValue(placeResponse.places)
            }, { error ->
                Log.d("zzx",error.stackTraceToString());
            })

        compositeDisposable.add(disposable)
    }
}