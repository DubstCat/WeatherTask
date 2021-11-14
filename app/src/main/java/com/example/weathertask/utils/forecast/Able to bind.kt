package com.example.weathertask

import com.example.weathertask.utils.forecast.ForecastItem

interface AbleToBind {
    fun bind(item: ForecastItem)
}
