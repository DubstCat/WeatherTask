package com.example.weathertask.presenters


import com.example.weathertask.retrofit.ForecastJsonResponse
import com.example.weathertask.retrofit.WeatherApi
import com.example.weathertask.utils.forecast.DaysOfTheWeek
import com.example.weathertask.utils.forecast.ForecastAdapter
import com.example.weathertask.utils.forecast.ForecastItem
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ForecastDataPresenter {

    val forecastObservable = PublishSubject.create<MutableList<ForecastItem>>()

    fun getForecast(city: String) {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherApi::class.java)

        val forecasts = mutableListOf<ForecastItem>()
        service.getForecast(city).subscribeOn(Schedulers.io()).subscribe( {forecastJsonResponse ->
            val responseList = mutableListOf<ForecastJsonResponse.List>()
            responseList.addAll(forecastJsonResponse.list?.toMutableList()!!)
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
            addDayText(forecasts)
            forecastObservable.onNext(forecasts)}, {throwable -> throwable.printStackTrace()}
        )
    }


    fun addDayText(list: MutableList<ForecastItem>) {
        var days = DaysOfTheWeek()
        days.setCurrentDay(getDaysIndex(getTodaysDayOfTheWeek()))
        list.add(0, ForecastItem(day = "Today", type = ForecastAdapter.ViewHolderType.TYPE_TEXT))
        var i = 1
        while (i < list.size - 1) {
            if (list[i].day != list[i + 1].day) {
                days++
                list.add(
                    i + 1,
                    ForecastItem(
                        day = days.getCurrentDay(),
                        type = ForecastAdapter.ViewHolderType.TYPE_TEXT
                    )
                )
                i += 2
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