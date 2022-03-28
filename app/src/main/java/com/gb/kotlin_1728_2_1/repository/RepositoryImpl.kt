package com.gb.kotlin_1728_2_1.repository

import com.gb.kotlin_1728_2_1.BuildConfig
import com.gb.kotlin_1728_2_1.model.Weather
import com.gb.kotlin_1728_2_1.model.WeatherDTO
import com.gb.kotlin_1728_2_1.model.getRussianCities
import com.gb.kotlin_1728_2_1.model.getWorldCities
import com.gb.kotlin_1728_2_1.model.utils.YANDEX_API_URL
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RepositoryImpl : RepositoryCitiesList, RepositoryDetails {

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

    private val retrofit =  Retrofit.Builder()
        .baseUrl(YANDEX_API_URL)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )).build().create(WeatherAPI::class.java)

    override fun getWeatherFromServer(lat:Double, lon:Double, callback: Callback<WeatherDTO>) {



        retrofit.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon).enqueue(callback)
    }
}



