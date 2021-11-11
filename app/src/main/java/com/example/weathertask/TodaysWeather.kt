package com.example.weathertask

import com.example.weathertask.utils.WindDegree

data class TodaysWeather(
    val city:String,
    val humidity: String,
    val rainfall: String,
    val pressure: String,
    val windSpeed: String,
    val windDegree: String,
    val tempAndWeather:String
)