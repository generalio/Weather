package com.generlas.weatherapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.generlas.weatherapp.R
import com.generlas.weatherapp.utils.MyObserver
import com.generlas.weatherapp.ui.activity.MainActivity
import com.generlas.weatherapp.ui.activity.WeatherActivity
import com.generlas.weatherapp.ui.adapter.PlaceAdapter
import com.generlas.weatherapp.utils.WeatherApplication
import com.generlas.weatherapp.viewmodel.PlaceViewModel

/**
 * description ： Place的fragment
 * date : 2025/3/13 14:38
 */
class PlaceFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class) }
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var placeRecyclerView: RecyclerView
    private lateinit var mEtSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        lifecycle.addObserver(MyObserver())

        placeRecyclerView = view.findViewById(R.id.place_rv)
        mEtSearch = view.findViewById(R.id.et_place_search)

        initEvent()
    }

    fun initEvent() {
        placeRecyclerView.layoutManager = LinearLayoutManager(activity)
        placeAdapter = PlaceAdapter(this)
        placeRecyclerView.adapter = placeAdapter
        placeAdapter.submitList(viewModel.placeList.toList())

        mEtSearch.addTextChangedListener {
            val content = mEtSearch.text.toString()
            if(content != "" || content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                placeRecyclerView.visibility = View.GONE
            }
        }

        viewModel.placeLiveData.observe(viewLifecycleOwner) { places ->
            if(!places.isNullOrEmpty()) {
                placeRecyclerView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                Log.d("zzx","(${places}:)-->>");
                placeAdapter.submitList(places.toList())
            } else {
                Toast.makeText(WeatherApplication.context, "尚未找到该城市", Toast.LENGTH_SHORT).show()
            }
        }
    }
}