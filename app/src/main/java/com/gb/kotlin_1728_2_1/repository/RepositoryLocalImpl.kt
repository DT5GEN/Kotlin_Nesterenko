package com.gb.kotlin_1728_2_1.repository

import com.gb.kotlin_1728_2_1.model.City
import com.gb.kotlin_1728_2_1.model.Weather
import com.gb.kotlin_1728_2_1.model.getRussianCities
import com.gb.kotlin_1728_2_1.model.getWorldCities
import com.gb.kotlin_1728_2_1.room.App
import com.gb.kotlin_1728_2_1.room.HistoryWeatherEntity

class RepositoryLocalImpl : RepositoryCitiesList, RepositoryHistoryWeather {

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
    override fun getAllHistoryWeather(): List<Weather> {
        return converterHistoryWeatherEntityToWeather(App.getHistoryWeatherDAO().getAllHistoryWeather())
    }

    override fun saveWeather(weather: Weather) {
        Thread{
            App.getHistoryWeatherDAO().insert(
                converterWeatherToHistoryWeatherEntity(weather)
            )
        }.start()

    }

    private fun converterHistoryWeatherEntityToWeather(entityList: List<HistoryWeatherEntity>):List<Weather>{


        return entityList.map { Weather(City(it.cityName, 0.0,0.0), it.temperature, it.feelsLike, it.icon) }
    }



    private fun converterWeatherToHistoryWeatherEntity(weather: Weather) =
        HistoryWeatherEntity(
            0,
            weather.city.name,
            weather.temperature,
            weather.feelsLike,
            weather.icon
        )

}



