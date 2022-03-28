package com.gb.kotlin_1728_2_1.repository

import com.gb.kotlin_1728_2_1.model.getRussianCities
import com.gb.kotlin_1728_2_1.model.getWorldCities
import com.gb.kotlin_1728_2_1.model.utils.YANDEX_API_KEY
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class RepositoryImpl : RepositoryCitiesList, RepositoryDetails {

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

    override fun getWeatherFromServer(url: String, callback: Callback) {
        val builder = Request.Builder().apply {
            header(YANDEX_API_KEY, com.gb.kotlin_1728_2_1.BuildConfig.WEATHER_API_KEY)
            //  YANDEX_API_URL + YANDEX_API_URL_END_POINT + "?lat=${localWeather.city.lat}&lon=${localWeather.city.lon}"
            url(url)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}



