package com.example.weathertask

import com.example.weathertask.utils.WindDegree

data class TodaysWeather(
    val city:String,
    val humidity: Int,
    val rainfall: Double,
    val pressure: Int,
    val windSpeed: Int,
    val windDegree: WindDegree
)