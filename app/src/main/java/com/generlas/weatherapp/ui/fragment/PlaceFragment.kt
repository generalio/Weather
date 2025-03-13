package com.generlas.weatherapp.ui.fragment

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
import com.generlas.weatherapp.databinding.FragmentPlaceBinding
import com.generlas.weatherapp.utils.MyObserver
import com.generlas.weatherapp.ui.activity.MainActivity
import com.generlas.weatherapp.ui.adapter.PlaceAdapter
import com.generlas.weatherapp.viewmodel.PlaceViewModel

/**
 * description ： Place的fragment
 * date : 2025/3/13 14:38
 */
class PlaceFragment : Fragment() {

    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!

    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class) }
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var placeRecyclerView: RecyclerView
    private lateinit var mEtSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(MyObserver())

        initView()
        initEvent()
    }

    fun initView() {
        placeRecyclerView = binding.placeRv
        mEtSearch = binding.etPlaceSearch
    }

    fun initEvent() {
        placeRecyclerView.layoutManager = LinearLayoutManager(activity)
        placeAdapter = PlaceAdapter()
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

        val mainActivity = activity as MainActivity
        viewModel.placeLiveData.observe(mainActivity) { places ->
            if(!places.isNullOrEmpty()) {
                placeRecyclerView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                Log.d("zzx","(${places}:)-->>");
                placeAdapter.submitList(places.toList())
            } else {
                Toast.makeText(mainActivity, "尚未找到该城市", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}