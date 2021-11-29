package com.example.weathertask.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weathertask.R
import com.example.weathertask.databinding.FragmentForecastBinding
import com.example.weathertask.presenters.ForecastDataPresenter
import com.example.weathertask.utils.CityObservable
import com.example.weathertask.utils.forecast.ForecastAdapter
import com.example.weathertask.utils.forecast.ForecastItem
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ForecastFragment : Fragment() {

    lateinit var binding: FragmentForecastBinding
    val adapter: ForecastAdapter = ForecastAdapter()
    val mPresenter = ForecastDataPresenter()
    var cityDisposable: Disposable? = null
    var adapterDisposable: Disposable? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false)
        binding.rvForecast.adapter = adapter

        val cityObserver = getCityObserver()
        val adapterObserver = getAdapterObserver()

        CityObservable.name.subscribe(cityObserver)
        mPresenter.forecastObservable.subscribeOn(Schedulers.io()).subscribe(adapterObserver)

        return binding.root
    }

    private fun getAdapterObserver(): Observer<MutableList<ForecastItem>> = object : Observer<MutableList<ForecastItem>>{
        override fun onSubscribe(d: Disposable?) {
            adapterDisposable = d
        }

        override fun onNext(t: MutableList<ForecastItem>?) {
            adapter.forecasts.clear()
            adapter.forecasts.addAll(t?: mutableListOf())
            adapter.notifyDataSetChanged()
        }

        override fun onError(e: Throwable?) {
            e?.printStackTrace()
        }

        override fun onComplete() {
            // pass
        }

    }

    private fun getCityObserver(): Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable?) {
            cityDisposable = d
        }

        override fun onNext(t: String?) {
            if (t != null) {
                binding.rvForecast.visibility = View.VISIBLE

                mPresenter.getForecast(t)
            }
        }

        override fun onError(e: Throwable?) {
            e?.printStackTrace()
        }

        override fun onComplete() {
            Log.d("ForecastCityObserver", "onComplete")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding.unbind()
        cityDisposable?.dispose()
        adapterDisposable?.dispose()
        cityDisposable = null
        adapterDisposable = null
    }
}