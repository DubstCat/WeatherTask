package com.example.weathertask.retrofit

import com.example.weathertask.utils.Constants.apikey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query



interface WeatherApi {

    //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

    @Headers("apppid:$apikey")
    @GET("weather")
    fun getTodaysWeather(@Query("lat") lat: Double, @Query("lon") lon: Double):Call<TodaysWeatherJsonResponse>
}