package com.example.weathertask.retrofit


class ForecastJsonResponse {
    var cod: String? = null
    var message: Int? = null
    var cnt: Int? = null
    var list: ArrayList<List>? = null
    var city: City? = null

    class City {
        var id: Int? = null
        var name: String? = null
        var coord: Coord? = null
        var country: String? = null
        var population: Int? = null
        var timezone: Int? = null
        var sunrise: Int? = null
        var sunset: Int? = null
    }

    class Clouds {
        var all: Int? = null
    }

    class Coord {
        var lat: Double? = null
        var lon: Double? = null
    }

    class List {
        var dt: Int? = null
        var main: Main? = null
        var weather: kotlin.collections.List<Weather>? = null
        var clouds: com.example.weathertask.Clouds? = null
        var wind: Wind? = null
        var visibility: Int? = null
        var pop: Double? = null
        var sys: Sys? = null
        var dtTxt: String? = null
        var rain: Rain? = null
        var snow: Snow? = null
    }

    class Main {
        var temp: Double? = null
        var feelsLike: Double? = null
        var tempMin: Double? = null
        var tempMax: Double? = null
        var pressure: Int? = null
        var seaLevel: Int? = null
        var grndLevel: Int? = null
        var humidity: Int? = null
        var tempKf: Int? = null
    }

    class Rain {
        private var _3h: Int? = null
        fun get3h(): Int? {
            return _3h
        }

        fun set3h(_3h: Int?) {
            this._3h = _3h
        }
    }

    class Snow {
        private var _3h: Double? = null
        fun get3h(): Double? {
            return _3h
        }

        fun set3h(_3h: Double?) {
            this._3h = _3h
        }
    }

    class Sys {
        var pod: String? = null
    }

    class Weather {
        var id: Int? = null
        var main: String? = null
        var description: String? = null
        var icon: String? = null
    }

    class Wind {
        var speed: Double? = null
        var deg: Int? = null
        var gust: Double? = null
    }

}

