package com.gb.kotlin_1728_2_1.model.utils

import android.os.Handler
import android.os.Looper
import com.gb.kotlin_1728_2_1.BuildConfig
import com.gb.kotlin_1728_2_1.lesson_5.MyApp
import com.gb.kotlin_1728_2_1.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val onWeatherLoader: OnWeatherLoader) {

    fun loadWeather(lat: Double, lon: Double) {

        Thread {  // TODO обернуть в try catch finally

            val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
            val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                requestMethod = "GET"
                readTimeout = 2000
                addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
            }

            val bufferedReader =
                BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val weatherDTO: WeatherDTO? =
                // данные с сервера JSON конвертируем из bufferedReader - буфера данных
                // через конвертер в один большой String,
                // далее используем импорт библиотеки в градле implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
                // всё это позволяет конвертировать большую строку в шаблон WeatherDTO::class.java
                // и на выходе мы получаем объект типа WeatherDTO, который передадим дальше через callback OnWeatherLoader
                Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java)
            //Handler(Looper.getMainLooper()).post {
            MyApp.superHandler.post {  // вариант с супер Хендлером, но экономии памяти при этом нет
                onWeatherLoader.onLoaded(weatherDTO)
            }
        }.start()
    }

    private fun convertBufferToResult(bufferedReader: BufferedReader): String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

    interface OnWeatherLoader {
        // чтобы принять данные weatherDTO через интерфейс, имплементируем медоды в DetailsFragment
        fun onLoaded(weatherDTO: WeatherDTO?)
        fun onFailed() // TODO HW
    }
}