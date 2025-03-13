package com.generlas.weatherapp.utils

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.generlas.weatherapp.repository.network.WeatherNetwork

/**
 * description ：Lifecycle
 * date : 2025/3/13 15:08
 */
class MyObserver : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        Log.d("zzx","(OnCreate:)-->>");
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.d("zzx","(OnStart:)-->>");
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.d("zzx","(OnResume:)-->>");
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.d("zzx","(OnPause:)-->>");
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.d("zzx","(onStop:)-->>");
    }

    override fun onDestroy(owner: LifecycleOwner) {
        WeatherNetwork.clearDisposable()
    }
}