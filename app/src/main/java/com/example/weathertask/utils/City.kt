package com.example.weathertask.utils


import io.reactivex.rxjava3.subjects.BehaviorSubject

object CityObservable {
    var name: BehaviorSubject<String> = BehaviorSubject.create()
}