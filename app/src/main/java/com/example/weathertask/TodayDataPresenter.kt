package com.example.weathertask

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
        val logger = HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
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
                response.body().let {
                    TODO("$it. - implement observable TodaysWeather")
                }
            }

            override fun onFailure(call: Call<TodaysWeatherJsonResponse>, t: Throwable) {
                t.printStackTrace()
                TODO("Implement showing error")
            }
        })
    }
}