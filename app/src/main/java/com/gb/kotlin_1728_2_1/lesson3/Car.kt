package com.gb.kotlin_1728_2_1.lesson3

class Car (val name:String, val age:Int) : Any() , FlyingVehicle{



    fun main(): Unit {

    }

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
}