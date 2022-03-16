package com.gb.kotlin_1728_2_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO создать проект в гит
        setContentView(R.layout.activity_main)
        val person = Person("noInit", 32)
        Log.d("TAG", "${person.name}")
    }
}



