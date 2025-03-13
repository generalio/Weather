package com.generlas.weatherapp.view.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.generlas.weatherapp.repository.model.Place
import com.generlas.weatherapp.repository.network.WeatherNetwork

/**
 * description ： 搜索城市的viewmodel
 * date : 2025/3/13 14:39
 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    var placeList = ArrayList<Place>()

    val placeLiveData = searchLiveData.switchMap { query ->
        WeatherNetwork.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}