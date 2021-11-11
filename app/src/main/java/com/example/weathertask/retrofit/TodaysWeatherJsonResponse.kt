package com.example.weathertask

import com.google.gson.annotations.SerializedName

class Clouds {
    @SerializedName("all")
    var all: Int? = null
}

class Coord {
    @SerializedName("lon")
    var lon: Int? = null

    @SerializedName("lat")
    var lat: Int? = null
}

class Main {
    @SerializedName("temp")
    var temp: Double? = null

    @SerializedName("feelslike")
    var feelsLike: Double? = null

    @SerializedName("temp_min")
    var tempMin: Double? = null

    @SerializedName("temp_max")
    var tempMax: Double? = null

    @SerializedName("pressure")
    var pressure: Int? = null

    @SerializedName("humidity")
    var humidity: Int? = null
}

class Sys {
    @SerializedName("type")
    var type: Int? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("message")
    var message: Double? = null

    @SerializedName("country")
    var country: String? = null

    @SerializedName("sunrise")
    var sunrise: Int? = null

    @SerializedName("sunset")
    var sunset: Int? = null
}

class TodaysWeatherJsonResponse {
    @SerializedName("coord")
    var coord: Coord? = null

    @SerializedName("weather")
    var weather: List<Weather>? = null

    @SerializedName("base")
    var base: String? = null

    @SerializedName("main")
    var main: Main? = null

    @SerializedName("wind")
    var wind: Wind? = null

    @SerializedName("clouds")
    var clouds: Clouds? = null

    @SerializedName("dt")
    var dt: Int? = null

    @SerializedName("sys")
    var sys: Sys? = null

    @SerializedName("timezone")
    var timezone: Int? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("cod")
    var cod: Int? = null

    private val additionalProperties: MutableMap<String, Any> = HashMap()

    fun getAdditionalProperties(): Map<String, Any> {
        return additionalProperties
    }

    fun setAdditionalProperty(name: String, value: Any) {
        additionalProperties[name] = value
    }
}

class Weather {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("main")
    var main: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("icon")
    var icon: String? = null
}

class Wind {
    @SerializedName("speed")
    var speed: Double? = null

    @SerializedName("deg")
    var deg: Double? = null
}