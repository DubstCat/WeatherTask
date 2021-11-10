package com.example.weathertask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.weathertask.R
import com.example.weathertask.TodayDataPresenter
import com.example.weathertask.TodaysWeather
import com.example.weathertask.databinding.FragmentTodayBinding


class TodayFragment : Fragment() {
    lateinit var binding: FragmentTodayBinding
    var mPresenter = TodayDataPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)
        // Inflate the layout for this fragment

        //mPresenter.getTodaysWeather(111.111, 111.111)

        binding.todaysWeather = TodaysWeather(
            city = "London",
            humidity = "57%",
            rainfall = "100mm",
            pressure = "1000 hPa",
            windSpeed = "24 km/h",
            weatherAndTemp = "Sunny | 22 `C",
            windDegree = "SE"
        )

        return binding.root


    }


}