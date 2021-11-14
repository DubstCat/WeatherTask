package com.example.weathertask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weathertask.R
import com.example.weathertask.databinding.FragmentForecastBinding
import com.example.weathertask.presenters.ForecastDataPresenter
import com.example.weathertask.utils.forecast.ForecastAdapter


class ForecastFragment : Fragment() {

    lateinit var binding: FragmentForecastBinding
    var mPresenter = ForecastDataPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false)

        mPresenter.getForecast("Minsk")

        binding.rvForecast.adapter = ForecastAdapter(mPresenter.forecasts.value!!.toList())

        return binding.root
    }
}