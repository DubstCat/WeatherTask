package com.example.weathertask.utils.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertask.AbleToBind
import com.example.weathertask.utils.forecast.ForecastAdapter.ViewHolderType.TYPE_TEXT
import com.example.weathertask.databinding.ItemDaytextBinding
import com.example.weathertask.databinding.ItemForecastBinding

class ForecastAdapter(val forecasts: List<ForecastItem>) :
    ListAdapter<ForecastItem, RecyclerView.ViewHolder>(
        ForecastDiffCallback()
    ) {


    class ForecastViewHolder(val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root), AbleToBind {
        override fun bind(item: ForecastItem) {
            binding.forecastItem = item
        }
    }

    class DayTextViewHolder(val binding: ItemDaytextBinding) :
        RecyclerView.ViewHolder(binding.root), AbleToBind {
        override fun bind(item: ForecastItem) {
            binding.forecastItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_TEXT) {
            val binding = ItemDaytextBinding.inflate(inflater, parent, false)
            DayTextViewHolder(binding)
        } else {
            val binding = ItemForecastBinding.inflate(inflater, parent, false)
            ForecastViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AbleToBind).bind(forecasts[position])
    }


    object ViewHolderType {
        val TYPE_TEXT = 2142461
        val TYPE_FORECAST = 6666666
    }

}