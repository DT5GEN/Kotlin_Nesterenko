package com.gb.kotlin_1728_2_1.room

import androidx.room.Entity
import androidx.room.PrimaryKey

const val ID = "id"
const val NAME = "NAME"
const val TEMPERATURE = "TEMPERATURE"
const val FEELS_LIKE = "FEELS_LIKE"
const val ICON = "ICON"


@Entity(tableName = "history_weather_entity")  //общепринятые правила названия файлов таблиц
data class HistoryWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cityName: String = "",
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val icon: String = ""
)
