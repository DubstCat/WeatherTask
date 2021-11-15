package com.example.weathertask.presenters


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weathertask.retrofit.ForecastJsonResponse
import com.example.weathertask.retrofit.WeatherApi
import com.example.weathertask.utils.forecast.ForecastAdapter
import com.example.weathertask.utils.forecast.ForecastItem
import com.example.weathertask.utils.today.TodaysWeather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class ForecastDataPresenter {
    private val retrofit: Retrofit
    private val service: WeatherApi
    val forecasts = mutableListOf<ForecastItem>()

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        service = retrofit.create(WeatherApi::class.java)
    }

    fun getForecast(city: String, adapter: ForecastAdapter):MutableList<ForecastItem> {
        service.getForecast(city).enqueue(object : Callback<ForecastJsonResponse> {
            override fun onResponse(
                call: Call<ForecastJsonResponse>,
                response: Response<ForecastJsonResponse>
            ) {
                if (response.isSuccessful) {

                    val responseList = mutableListOf<ForecastJsonResponse.List>()
                    responseList.addAll(response.body()?.list?.toMutableList()!!)
                    responseList.forEach {
                        forecasts.add(
                            ForecastItem(
                                timestamp = it.dtTxt,
                                temp = it.main?.temp?.toInt().toString()+ "`C",
                                weather = it.weather?.get(0)?.description
                            )
                        )
                    }
                        adapter.forecasts.addAll(forecasts)
                        adapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<ForecastJsonResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return forecasts
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}