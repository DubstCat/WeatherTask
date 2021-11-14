package com.example.weathertask.utils.forecast

import androidx.recyclerview.widget.DiffUtil

class ForecastDiffCallback : DiffUtil.ItemCallback<ForecastItem>() {
    override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean =
        oldItem == newItem

}