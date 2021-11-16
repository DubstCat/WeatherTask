package com.example.weathertask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathertask.utils.City
import com.example.weathertask.R
import com.example.weathertask.databinding.FragmentForecastBinding
import com.example.weathertask.presenters.ForecastDataPresenter
import com.example.weathertask.utils.forecast.ForecastAdapter
import com.example.weathertask.utils.forecast.ForecastItem

class ForecastFragment : Fragment() {

    lateinit var binding: FragmentForecastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mPresenter = ForecastDataPresenter()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false)

        binding.rvForecast.layoutManager = LinearLayoutManager(context)

        val list = mutableListOf<ForecastItem>()
        val adapter = ForecastAdapter(list)
        mPresenter.getForecast(City.name, adapter)
        binding.rvForecast.adapter = adapter

        return binding.root
    }
}