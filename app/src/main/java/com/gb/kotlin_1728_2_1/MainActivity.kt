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
        person.newProperty= ""
        Log.d("TAG", "${person.newProperty}")

        Repository.getData()
        val callback = object : View.OnClickListener{
            override fun onClick(p0: View?) {
                TODO("Not yet implemented")
            }

        }

        Person.test
        Person.testPublicStaticFinal
        Person.testCompan()

        val cat = object {
            val name = "Kisa"
            val age = 3
        }
        Log.d("CAT", " Cat name ${cat.name} older ${cat.age}")
    }
}



