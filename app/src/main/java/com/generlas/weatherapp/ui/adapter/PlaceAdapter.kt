package com.generlas.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.generlas.weatherapp.databinding.PlaceItemBinding
import com.generlas.weatherapp.bean.Place

/**
 * description ： place的Adapter
 * date : 2025/3/13 14:36
 */
class PlaceAdapter(): ListAdapter<Place, PlaceAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Place>() {
    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.location == newItem.location
    }

    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }

})  {

    inner class ViewHolder(binding: PlaceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val placeName: TextView = binding.tvPlaceName
        val placeAddress: TextView = binding.tvPlaceAddress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = getItem(position)
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

}