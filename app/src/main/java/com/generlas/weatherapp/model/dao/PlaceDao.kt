package com.generlas.weatherapp.model.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.generlas.weatherapp.model.bean.Place
import com.generlas.weatherapp.utils.WeatherApplication
import com.google.gson.Gson

/**
 * description ： TODO:类的作用
 * date : 2025/3/15 13:19
 */
object PlaceDao {

    val sharedPreferences = WeatherApplication.context.getSharedPreferences("place", Context.MODE_PRIVATE)
    fun savePlace(place: Place) {
        sharedPreferences.edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences.getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences.contains("place")

}