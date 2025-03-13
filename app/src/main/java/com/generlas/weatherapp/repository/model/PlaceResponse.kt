package com.generlas.weatherapp.repository.model

import com.google.gson.annotations.SerializedName

/**
 * description ： place数据类
 * date : 2025/3/13 14:32
 */
data class PlaceResponse(val status: String, val places: List<Place>)
data class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)
data class Location(val lng: String, val lat: String)
