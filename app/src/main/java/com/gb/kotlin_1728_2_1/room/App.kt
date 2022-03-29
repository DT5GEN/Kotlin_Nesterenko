package com.gb.kotlin_1728_2_1.room

import android.app.Application
import androidx.room.Room
import java.util.*

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        const val DB_NAME = "History.db"
        private var db:HistoryDatabase? = null

        fun getHistoryWeatherDAO():HistoryWeatherDAO {
            if (db == null) {
                if (appInstance == null) {
                    throw IllformedLocaleException("Всё очень плохо!")
                } else {
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDatabase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()  // TODO  заверяем систему, что не сильно навредим главному потоку :)
                        .build()
                }
            }
            return db!!.historyWeatherDAO()
        }
    }
}