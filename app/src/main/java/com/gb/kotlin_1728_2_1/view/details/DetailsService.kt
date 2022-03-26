package com.gb.kotlin_1728_2_1.view.details

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gb.kotlin_1728_2_1.BuildConfig
import com.gb.kotlin_1728_2_1.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class DetailsService(name: String = "") : IntentService(name) {


    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val lat = intent.getDoubleExtra(BUNDLE_KEY_LAT, 0.0)
            val lon = intent.getDoubleExtra(BUNDLE_KEY_LON, 0.0)

            loadWeather(lat, lon)
        }

    }

    private fun loadWeather(lat: Double, lon: Double) {

        try {
            val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
            val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                requestMethod = "GET"
                readTimeout = 2000
                addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
            }
            try {
                val bufferedReader =
                    BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val weatherDTO: WeatherDTO? =
                // данные с сервера JSON конвертируем из bufferedReader - буфера данных
                // через конвертер в один большой String,
                // далее используем импорт библиотеки в градле implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
                // всё это позволяет конвертировать большую строку в шаблон WeatherDTO::class.java
                    // и на выходе мы получаем объект типа WeatherDTO, который передадим дальше через callback OnWeatherLoader
                    Gson().fromJson(
                        convertBufferToResult(bufferedReader),
                        WeatherDTO::class.java
                    )

                // кричим на весь телефон
//                sendBroadcast(Intent(BROADCAST_ACTION).apply {
//                    putExtra(BUNDLE_KEY_WEATHER, weatherDTO)
//                })
                //   кричим только в рамках приложения
                LocalBroadcastManager.getInstance(applicationContext)
                    .sendBroadcast(Intent(BROADCAST_ACTION).apply {
                        putExtra(BUNDLE_KEY_WEATHER, weatherDTO)
                    })

            } catch (e: Exception) {
                // TODO заменить onWeatherLoader.onFailed()
                Log.e("e", "Fail connection", e)
                e.printStackTrace()
            } finally {
                httpsURLConnection.disconnect()
            }


        } catch (e: MalformedURLException) {
            Log.e("i", "Fail URI", e)
            e.printStackTrace()
        }

    }

    private fun convertBufferToResult(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

}