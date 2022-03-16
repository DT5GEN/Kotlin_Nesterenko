package com.gb.kotlin_1728_2_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO создать проект в гит
        setContentView(R.layout.activity_main)
        val person = Person("noInit", 32)
        person.newProperty = ""
        Log.d("TAG", "${person.newProperty}")

        Repository.getData()
        val callback = object : View.OnClickListener {
            override fun onClick(p0: View?) {
                TODO("Not yet implemented")
            }

        }

        Person.test
        Person.testPublicStaticFinal
        Person.testCompan()

        var result = if (false) {
            1
        } else {
            2
        }
        Log.d("TAG", "$result")

        result = when (WeatherType.CLOUDY) {
            WeatherType.SUNNY -> 1
            WeatherType.RAINY -> 2
            WeatherType.CLOUDY -> 3
        }

        Log.d("TAG", "$result")

        for (i in 0..20 step 2) {
            Log.d("TAG", "$i")
        }
        for (i in 0 until 20 step 5) {
            Log.d("TAG", "$i")
        }
        for (i in 20 downTo 0 step 2) {
            Log.d("TAG", "$i")
        }

        repeat(6) {
            Log.d("TAG", "${it + 1}")
        }

        val dayzOfWeek =
            listOf("sunday", "monday", "Tuesday", "Wednesday", "Thyrsday", "Friday", "Saturday")
        dayzOfWeek.forEach{
            Log.d("TAG", it)
        }
        for(day in dayzOfWeek) {
            Log.d("TAG", day)
        }

        val cat = object {
            val name = "Kisa"
            val age = 3
        }
        Log.d("CAT", " Cat name ${cat.name} older ${cat.age}")
    }

    val notes = NoteKotlin("", 1)
    val copyNote = notes.copy("New")

    enum class WeatherType {
        SUNNY,
        RAINY,
        CLOUDY
    }

}



