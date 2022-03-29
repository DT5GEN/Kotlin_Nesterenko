package com.gb.kotlin_1728_2_1.room

import androidx.room.*

@Dao
interface HistoryWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryWeatherEntity)

    @Delete
    fun delete(entity: HistoryWeatherEntity)

    @Update
    fun update(entity: HistoryWeatherEntity)

    @Query("select * FROM history_weather_entity")
    fun getAllHistoryWeather(): List<HistoryWeatherEntity>


    // fun getAllHistoryWeather() // TODO сделать запрос по какому-то из полей
}