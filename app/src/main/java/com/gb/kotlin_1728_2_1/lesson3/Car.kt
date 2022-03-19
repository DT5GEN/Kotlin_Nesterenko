package com.gb.kotlin_1728_2_1.lesson3

import android.util.Log
import android.view.ViewGroup

class Car(val name: String, val age: Int) : Any(), FlyingVehicle {


    override val greeting: String
        get() {
            return super.greeting
        }

    override val maxHeight: Float
        get() = 6000f
    override val maxSpeed: Float
        get() = 300f

    override fun speed(weight: Float): Float {
        return maxSpeed
    }


    fun main(): Unit {
        writeInt(1)
        writeDouble(1.0)
        writeString("1")


        write(1)
        write(1.0)
        write("1")
    }
    fun writeInt(input:Int) = Log.d("mylog", input.toString())
    fun writeString(input:String) = Log.d("mylog", input.toString())
    fun writeDouble(input:Double) = Log.d("mylog", input.toString())


    fun <T> write(input:T) = Log.d("mylog", input.toString())
    fun <T:Any> write2(input:T) = Log.d("mylog", input.toString())
    // <T> и <T:Any> в данной ситуации принимают все типы
    fun <T:ViewGroup> write(input:T) = Log.d("mylog", input.toString())
    // дженерик принимает ограниченные  ViewGroup тип


}

class Producer<T>{
    private val  hack = mutableListOf<T>()
    fun produce():T{
        return hack.last()
    }
}