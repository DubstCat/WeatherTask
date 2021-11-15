package com.example.weathertask.utils.forecast

class DaysOfTheWeek {
    val days = arrayOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    )

    private var currentIndex = 0

    operator fun inc() = this.also {
        currentIndex++
        if (currentIndex == days.size) {
            currentIndex = 0
        }
    }

    fun getCurrentDay() = days[currentIndex]

    fun setCurrentDay(i:Int){
        currentIndex = i
    }
}