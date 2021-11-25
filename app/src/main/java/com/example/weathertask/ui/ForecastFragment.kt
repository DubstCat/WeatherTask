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
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class ForecastFragment : Fragment() {

    lateinit var binding: FragmentForecastBinding
    val adapter: ForecastAdapter = ForecastAdapter()
    val mPresenter = ForecastDataPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false)
        binding.rvForecast.adapter = adapter

        CityObservable.name.subscribe(getAdapterObserver())
        return binding.root
    }

    private fun getAdapterObserver(): Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable?) {
            // pass
        }

        override fun onNext(t: String?) {
            if (t != null) {
                binding.rvForecast.visibility = View.VISIBLE
                adapter.forecasts.clear()
                adapter.notifyDataSetChanged()
                mPresenter.getForecast(t, adapter)
            }
        }

        override fun onError(e: Throwable?) {
            e?.printStackTrace()
        }

        override fun onComplete() {
            Log.d("ForecastCityObserver", "onComplete")
        }
    }
}