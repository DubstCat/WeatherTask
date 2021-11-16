package com.example.weathertask.utils.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertask.R
import com.example.weathertask.utils.forecast.ForecastAdapter.ViewHolderType.TYPE_TEXT
import com.example.weathertask.databinding.ItemDaytextBinding
import com.example.weathertask.databinding.ItemForecastBinding
import com.example.weathertask.utils.forecast.ForecastAdapter.ViewHolderType.TYPE_FORECAST

class ForecastAdapter(var forecasts: MutableList<ForecastItem>) :
    ListAdapter<ForecastItem, RecyclerView.ViewHolder>(
        ForecastDiffCallback()
    ) {


    class ForecastViewHolder(val view: View) :
        RecyclerView.ViewHolder(view), AbleToBind {
        private val binding = DataBindingUtil.bind<ItemForecastBinding>(view)

        override fun bind(item: ForecastItem?) {
            binding?.forecastItem = item
            binding?.ivForecastWeather?.setImageResource(
                when (item?.weather) {
                    "Clear" -> R.drawable.weather_sunny
                    "Clouds" -> R.drawable.weather_cloudy
                    "Rain" -> R.drawable.weather_rainy
                    "Snow" -> R.drawable.weather_snowy
                    "Hail" -> R.drawable.weather_hail
                    "Pouring" -> R.drawable.weather_pouring
                    "Storm " -> R.drawable.weather_lightning
                    else -> R.drawable.weather_sunny
                }
            )
        }
    }

    class DayTextViewHolder(val view: View) :
        RecyclerView.ViewHolder(view), AbleToBind {
        private val binding = DataBindingUtil.bind<ItemDaytextBinding>(view)

        override fun bind(item: ForecastItem?) {
            binding?.forecastItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_TEXT) {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(viewType, parent, false)
            DayTextViewHolder(view)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(viewType, parent, false)
            ForecastViewHolder(view)
        }
    }

    override fun getItemCount(): Int = forecasts.size
    override fun getItemViewType(position: Int): Int {
        return forecasts[position].type ?: TYPE_FORECAST
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AbleToBind).bind(forecasts[position])
    }


    object ViewHolderType {
        val TYPE_TEXT = R.layout.item_daytext
        val TYPE_FORECAST = R.layout.item_forecast
    }

}