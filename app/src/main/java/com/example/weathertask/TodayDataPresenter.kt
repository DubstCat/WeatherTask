package com.example.weathertask

import android.util.Log
import com.example.weathertask.retrofit.TodaysWeatherJsonResponse
import com.example.weathertask.retrofit.WeatherApi
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class TodayDataPresenter {
    private val retrofit: Retrofit
    private val service: WeatherApi
    lateinit var todaysWeather:TodaysWeather

    init {

        retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(WeatherApi::class.java)
    }

    fun getTodaysWeather(lat: Double, lon: Double) {
        service.getTodaysWeather(lat, lon).enqueue(object : Callback<TodaysWeatherJsonResponse> {
            override fun onResponse(
                call: Call<TodaysWeatherJsonResponse>,
                response: Response<TodaysWeatherJsonResponse>
            ) {

                TODO("Not implemented")
            }

            override fun onFailure(call: Call<TodaysWeatherJsonResponse>, t: Throwable) {
                //t.printStackTrace()
                TODO("Implement showing error")
            }
        })
    }
}