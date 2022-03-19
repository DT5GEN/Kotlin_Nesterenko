package com.gb.kotlin_1728_2_1.lesson3

interface FlyingVehicle {

    val maxHeight: Float
    val maxSpeed: Float

    val greeting: String
        get() = "Beep-beep"

    fun speed(weight: Float): Float
}