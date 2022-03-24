package com.gb.kotlin_1728_2_1.lesson_5

import android.app.Application
import android.os.Handler
import android.os.Looper

class MyApp : Application(){

//    override fun onCreate() {
//        super.onCreate()
//    }

    companion object{
        val superHandler = Handler(Looper.getMainLooper())
    }
}