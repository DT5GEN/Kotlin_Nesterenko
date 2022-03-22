package com.gb.kotlin_1728_2_1.lesson4

import android.util.Log

class LambdaKotlin {

    fun  main() {
      val str =  "String ( )"
        val charCount= str.length
        val charCount2= str.length()
        Log.d("my log", "$charCount")
        Log.d("my log", "$charCount2")
        Log.d("my log", "${str.compareTo(str)}")
        Log.d("my log", "${str.compareTo(str)}")
    }
    //fun String.charCount
    fun String.length():Int{  // Экстеншн функция - инструмент, который помогает внедрить функцию свою новую в любой существующий класс
        var counter = 0
        for (ch in this){
            if (ch !=' ') counter++
        }
        return counter
    }

    fun String.compareTo():Int{

        return -1
    }

}