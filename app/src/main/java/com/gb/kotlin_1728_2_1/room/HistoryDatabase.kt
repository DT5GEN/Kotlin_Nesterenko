package com.gb.kotlin_1728_2_1.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [HistoryWeatherEntity::class], version = 4, exportSchema = false )
abstract class HistoryDatabase: RoomDatabase() { // Обёртка над SQL Lite. Cоздаётся экземпляр абстрактный класс базы данных
    // задаётся интерфейс по работе с этой базой данных HistoryWeatherDAO
    // и таблица HistoryWeatherEntity

    abstract fun historyWeatherDAO():HistoryWeatherDAO
}