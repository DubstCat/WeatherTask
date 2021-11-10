package com.example.weathertask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weathertask.R
import com.example.weathertask.databinding.FragmentForecastBinding
import com.example.weathertask.retrofit.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ForecastFragment : Fragment() {

    lateinit var binding: FragmentForecastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


}