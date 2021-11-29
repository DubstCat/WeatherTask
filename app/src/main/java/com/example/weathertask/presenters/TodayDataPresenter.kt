package com.example.weathertask.presenters

import com.example.weathertask.retrofit.WeatherApi
import com.example.weathertask.utils.today.TodaysWeather
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class TodayDataPresenter {
    lateinit var todaysWeather: TodaysWeather
    var todaysWeatherObservable = PublishSubject.create<TodaysWeather>()


    fun getTodaysWeather(city: String) {
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

        service.getTodaysWeather(city).subscribeOn(Schedulers.io()).subscribe({
            todaysWeather = TodaysWeather(
                city = it.name.toString() + ", " +it.sys?.country,
                humidity = it.main?.humidity?.toString() + " %",
                rainfall = "1 mm",
                pressure = it.main?.pressure.toString() + " hPa",
                windSpeed = it.wind?.speed?.toInt().toString() + " kmH",
                windDegree = getTextDegree(it.wind?.deg?.toInt()),
                tempAndWeather = (it.main?.temp?.toInt()
                    ?.minus(273)).toString()
                        + "°C | " +
                        (it.weather?.get(0)?.main ?: ("")),
                weather = it.weather?.get(0)?.main ?: ("")
            )
            todaysWeatherObservable.onNext(todaysWeather)
        }, {throwable -> throwable.printStackTrace()})
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