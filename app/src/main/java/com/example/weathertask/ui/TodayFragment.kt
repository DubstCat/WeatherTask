package com.example.weathertask.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weathertask.R
import com.example.weathertask.databinding.FragmentTodayBinding
import com.example.weathertask.presenters.TodayDataPresenter
import com.example.weathertask.utils.CityObservable
import com.example.weathertask.utils.today.TodaysWeather
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable


class TodayFragment : Fragment() {
    lateinit var binding: FragmentTodayBinding


    var mPresenter = TodayDataPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)


        CityObservable.name.subscribe(object : Observer<String> {
            override fun onNext(t: String?) {
                getWeatherOnLocation(t ?: "London")
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
            }

            override fun onComplete() {
                Log.d("CityObservable", "onComplete")
            }

            override fun onSubscribe(d: Disposable?) {
                // pass
            }

        })


        mPresenter.todaysWeatherObservable.subscribe(object : Observer<TodaysWeather> {
            override fun onNext(it: TodaysWeather?) {
                binding.todaysWeather = it
                binding.ivMain.setImageResource(
                    when (binding.todaysWeather?.weather) {
                        "Clear" -> R.drawable.weather_sunny
                        "Rain" -> R.drawable.weather_rainy
                        "Snow" -> R.drawable.weather_snowy
                        "Hail" -> R.drawable.weather_hail
                        "Wind" -> R.drawable.weather_windy
                        "Clouds" -> R.drawable.weather_cloudy
                        else -> R.drawable.weather_sunny
                    }
                )
            }


            override fun onError(e: Throwable?) {
                e?.printStackTrace()
            }

            override fun onComplete() {
                Log.d("TodaysObservable", "Complete")
            }

            override fun onSubscribe(d: Disposable?) {
                // pass
            }
        })


        binding.tvShare.setOnClickListener {
            onShareWeather()
        }
        return binding.root
    }

    /**
     * Function for getting new data on location
     * */
    fun getWeatherOnLocation(city: String) {
        mPresenter.getTodaysWeather(city)
    }


    private fun onShareWeather() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Today is ${binding.todaysWeather?.tempAndWeather} in ${binding.todaysWeather?.city}"
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}