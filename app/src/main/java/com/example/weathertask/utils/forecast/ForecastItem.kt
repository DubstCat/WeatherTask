package com.example.weathertask.utils.forecast

data class ForecastItem(
    val day: String? = null,
    val timestamp: String? = null,
    val weather: String? = null,
    val temp: Int? = null,
    val type: Int? = null
)