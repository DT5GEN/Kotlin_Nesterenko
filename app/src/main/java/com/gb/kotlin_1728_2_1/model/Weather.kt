package com.gb.kotlin_1728_2_1.model

data class Weather(val city:City = getDefaultCity(), val temperature:Int = 15, val feelsLike:Int = 14)

data class City(val name: String, val lat: Double, val lon: Double)

fun getDefaultCity() = City("Moscow", 55.5, 37.45)