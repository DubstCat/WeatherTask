package com.example.weathertask.retrofit

import com.example.weathertask.TodaysWeatherJsonResponse
import com.example.weathertask.utils.Constants.apikey
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.*


//api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

interface WeatherApi {


    @GET("weather")
    fun getTodaysWeather(
        @Query("q") city: String,
        @Query("appid") appid: String = apikey

    ): Observable<TodaysWeatherJsonResponse>

    @GET("forecast")
    fun getForecast(
        @Query("q") city: String,
        @Query("appid") appid: String = apikey
    ): Observable<ForecastJsonResponse>
}