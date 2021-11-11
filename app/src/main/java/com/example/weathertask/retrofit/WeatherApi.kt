package com.example.weathertask.retrofit

import com.example.weathertask.TodaysWeatherJsonResponse
import com.example.weathertask.utils.Constants.apikey
import retrofit2.Call
import retrofit2.http.*


//api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

interface WeatherApi {


    @GET("weather")
    fun getTodaysWeather(
        @Query("lat") lat: Int,
        @Query("lon") lon: Int,
        @Query("appid")appid:String = apikey

    ): Call<TodaysWeatherJsonResponse>
}