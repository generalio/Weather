package com.generlas.weatherapp.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.generlas.weatherapp.R
import com.generlas.weatherapp.model.bean.Place
import com.generlas.weatherapp.ui.activity.WeatherActivity
import com.generlas.weatherapp.ui.fragment.PlaceFragment
import com.generlas.weatherapp.utils.WeatherApplication

/**
 * description ： place的Adapter
 * date : 2025/3/13 14:36
 */
class PlaceAdapter(val fragment: PlaceFragment): ListAdapter<Place, PlaceAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Place>() {
    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.location == newItem.location
    }

    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }

})  {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.tv_placeName)
        val placeAddress: TextView = view.findViewById(R.id.tv_placeAddress)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                val place = getItem(position)
                val activity = fragment.activity
                if(activity is WeatherActivity) {
                    activity.drawerLayout.closeDrawers()
                    activity.viewModel.locationLng = place.location.lng
                    activity.viewModel.locationLat = place.location.lat
                    activity.viewModel.placeName = place.name
                    activity.refreshWeather()
                } else {
                    val intent = Intent(WeatherApplication.context, WeatherActivity::class.java).apply {
                        putExtra("location_lng", place.location.lng)
                        putExtra("location_lat", place.location.lat)
                        putExtra("place_name", place.name)
                    }

                    fragment.startActivity(intent)
                    activity?.finish() //退出当前activity
                }
                fragment.viewModel.savePlace(place)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = getItem(position)
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

}