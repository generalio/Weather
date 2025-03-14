package com.generlas.weatherapp.ui.activity

import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.generlas.weatherapp.R
import com.generlas.weatherapp.bean.Aqi
import com.generlas.weatherapp.bean.DailyResponse
import com.generlas.weatherapp.bean.Realtime
import com.generlas.weatherapp.utils.getSky
import com.generlas.weatherapp.viewmodel.PlaceViewModel
import com.generlas.weatherapp.viewmodel.WeatherViewModel
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    lateinit var placeName: TextView
    lateinit var currentTemp: TextView
    lateinit var currentSky: TextView
    lateinit var currentAqi: TextView

    lateinit var dateInfo: TextView
    lateinit var skyIcon: ImageView
    lateinit var skyInfo: TextView
    lateinit var temperatureInfo: TextView

    lateinit var coldRiskText: TextView
    lateinit var dressingText: TextView
    lateinit var ultravioletText: TextView
    lateinit var carWashingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        //将Activity布局设置在顶栏上并将顶栏设置为透明
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        window.statusBarColor = Color.TRANSPARENT

        if(viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if(viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if(viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.realtimeLiveData.observe(this) {result ->
            if(result != null) {
                showWeatherRealtime(result)
            } else {
                Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.dailyLiveData.observe(this) {result ->
            if(result != null) {
                showWeatherDaily(result)
            } else {
                Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getDaily(viewModel.locationLng, viewModel.locationLat)
        viewModel.getRealtime(viewModel.locationLng, viewModel.locationLat)
    }

    private fun showWeatherRealtime(realtime: Realtime) {
        placeName = findViewById(R.id.placeName)
        currentTemp = findViewById(R.id.currentTemp)
        currentSky = findViewById(R.id.currentSky)
        currentAqi = findViewById(R.id.currentAQI)

        placeName.text = viewModel.placeName
        val currentTempText = "${realtime.temperature.toInt()} °C"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.air_quality.aqi.chn.toInt()}"
        currentAqi.text = currentPM25Text
        val nowLayout: View = findViewById(R.id.nowLayout)
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
    }

    private fun showWeatherDaily(daily: DailyResponse.Daily) {
        val forecastLayout: LinearLayout = findViewById(R.id.forecastLayout)

        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for(i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            dateInfo = view.findViewById(R.id.dateInfo)
            skyIcon = view.findViewById(R.id.skyIcon)
            skyInfo = view.findViewById(R.id.skyInfo)
            temperatureInfo = view.findViewById(R.id.temperatureInfo)
            dateInfo.text = skycon.date
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }

        // 填充life_index.xml布局中的数据
        coldRiskText = findViewById(R.id.coldRiskText)
        dressingText = findViewById(R.id.dressingText)
        ultravioletText = findViewById(R.id.ultravioletText)
        carWashingText = findViewById(R.id.carWashingText)

        val weatherLayout: View = findViewById(R.id.weatherLayout)
        val lifeIndex = daily.life_index
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }
}