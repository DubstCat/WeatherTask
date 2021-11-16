package com.example.weathertask.presenters


import com.example.weathertask.retrofit.ForecastJsonResponse
import com.example.weathertask.retrofit.WeatherApi
import com.example.weathertask.utils.forecast.DaysOfTheWeek
import com.example.weathertask.utils.forecast.ForecastAdapter
import com.example.weathertask.utils.forecast.ForecastItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    fun getForecast(city: String, adapter: ForecastAdapter): MutableList<ForecastItem> {
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
                                timestamp = it.dtTxt?.subSequence(11, 16)?.toString(),
                                temp = (it.main?.temp?.toInt()?.minus(273)).toString() + "°C",
                                weather = it.weather?.get(0)?.main,
                                day = it.dtTxt?.subSequence(8, 10)?.toString()
                            )
                        )
                    }
                    adapter.forecasts.addAll(forecasts)
                    addDayText(adapter.forecasts)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ForecastJsonResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return forecasts
    }


    fun addDayText(list: MutableList<ForecastItem>) {
        var days = DaysOfTheWeek()
        days.setCurrentDay(getDaysIndex(getTodaysDayOfTheWeek()))
        list.add(0, ForecastItem(day = "Today", type = ForecastAdapter.ViewHolderType.TYPE_TEXT))
        var i = 1
        while (i < list.size-1) {
            val day = days.getCurrentDay()
            if (list[i].day != list[i + 1].day) {
                list.add(
                    i+1,
                    ForecastItem(day = day, type = ForecastAdapter.ViewHolderType.TYPE_TEXT)
                )
                days++
                i+=2
            }
            i++
        }
    }

    private fun getTodaysDayOfTheWeek(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            Calendar.SUNDAY -> "Sunday"
            else -> ""
        }
    }

    private fun getDaysIndex(day: String): Int = when (day) {
        "Monday" -> 0
        "Tuesday" -> 1
        "Wednesday" -> 2
        "Thursday" -> 3
        "Friday" -> 4
        "Saturday" -> 5
        "Sunday" -> 6
        else -> 0

    }
}