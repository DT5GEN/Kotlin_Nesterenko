package com.gb.kotlin_1728_2_1.room

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        const val DB_NAME = "History.db"
        private var db: HistoryDatabase? = null

        fun getHistoryWeatherDAO(): HistoryWeatherDAO {
            if (db == null) {
                if (appInstance == null) {
                    throw IllformedLocaleException("Всё очень плохо!")
                } else {
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDatabase::class.java,
                        DB_NAME
                    )
                        // .allowMainThreadQueries()  // TODO  заверяем систему, что не сильно навредим главному потоку :) (решено!)
                        .addMigrations(object : Migration(1, 2) {
                            override fun migrate(database: SupportSQLiteDatabase) {
                                //database.execSQL("ALTER TABLE history_weather_entity ADD COLUMN icon2 TEXT NOT NULL DEFAULT ''")
                                database.execSQL("ALTER TABLE history_weather_entity RENAME COLUMN icon TO icon2")
                            }

                        }).addMigrations(object : Migration(2, 3) {
                            override fun migrate(database: SupportSQLiteDatabase) {
//                                database.execSQL("ALTER TABLE history_weather_entity ADD COLUMN icon2 TEXT NOT NULL DEFAULT ''")
                                database.execSQL("ALTER TABLE history_weather_entity RENAME COLUMN icon2 TO icon3")
                            }

                        }).addMigrations(object : Migration(3, 4) {
                            override fun migrate(database: SupportSQLiteDatabase) {
//                                database.execSQL("ALTER TABLE history_weather_entity ADD COLUMN icon2 TEXT NOT NULL DEFAULT ''")
                                database.execSQL("ALTER TABLE history_weather_entity RENAME COLUMN icon3 TO icon4")
                            }

                        })
                        .build()
                }
            }
            return db!!.historyWeatherDAO()
        }
    }
}