package com.example.weathertask.presenters

import androidx.lifecycle.MutableLiveData
import com.example.weathertask.utils.today.TodaysWeather
import com.example.weathertask.TodaysWeatherJsonResponse
import com.example.weathertask.retrofit.WeatherApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor


class TodayDataPresenter {
    private val retrofit: Retrofit
    private val service: WeatherApi
    lateinit var todaysWeather:TodaysWeather
    var todaysWeatherObservable = PublishSubject.create<TodaysWeather>()

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

    fun getTodaysWeather(city:String) {
        service.getTodaysWeather(city)
            .enqueue(object : Callback<TodaysWeatherJsonResponse> {
                override fun onResponse(
                    call: Call<TodaysWeatherJsonResponse>,
                    response: Response<TodaysWeatherJsonResponse>
                ) {
                    todaysWeather = TodaysWeather(
                        city = response.body()?.name.toString() + ", " + response.body()?.sys?.country,
                        humidity = response.body()?.main?.humidity?.toString() + " %",
                        rainfall = "1 mm",
                        pressure = response.body()?.main?.pressure.toString() + " hPa",
                        windSpeed = response.body()?.wind?.speed?.toInt().toString() + " kmH",
                        windDegree = getTextDegree(response.body()?.wind?.deg?.toInt()),
                        tempAndWeather = (response.body()?.main?.temp?.toInt()
                            ?.minus(273)).toString()
                                + "°C | " +
                                (response.body()?.weather?.get(0)?.main ?: ("")),
                        weather = response.body()?.weather?.get(0)?.main?:("")
                    )
                        todaysWeatherObservable.onNext(todaysWeather)
                }

                override fun onFailure(call: Call<TodaysWeatherJsonResponse>, t: Throwable) {
                    t.printStackTrace()

                }
            })
    }



    fun getTextDegree(deg: Int?) =
        when (deg) {
            in 0..29, in 330..360 -> "N"
            in 30..59 -> "NE"
            in 60..119 -> "E"
            in 120..149 -> "SE"
            in 150..209 -> "S"
            in 210..239 -> "SW"
            in 240..299 -> "W"
            in 300..329 -> "NW"
            else -> "no data"
        }


}