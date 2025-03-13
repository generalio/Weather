package com.generlas.weatherapp.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * description ： 全局的Application
 * date : 2025/3/13 10:21
 */
class WeatherApplication : Application() {

    @SuppressLint("StaticFieldLeak")
    companion object {
        const val TOKEN = "UlHjuVu0edJvnIpB"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}