package com.example.weathertask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.weathertask.R
import com.example.weathertask.databinding.FragmentForecastBinding
import com.example.weathertask.presenters.ForecastDataPresenter
import com.example.weathertask.utils.CityObservable
import com.example.weathertask.utils.forecast.ForecastAdapter
import com.example.weathertask.utils.forecast.ForecastItem

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

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

        binding.rvForecast.adapter = adapter

        CityObservable.name.subscribe(object:Observer<String>{
            override fun onSubscribe(d: Disposable?) {
                // pass
            }

            override fun onNext(t: String?) {
                mPresenter.getForecast(t?:"London", adapter)
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
            }

            override fun onComplete() {
                Log.d("ForecastCityObserver", "onComplete")
            }

        } )


        return binding.root
    }
}