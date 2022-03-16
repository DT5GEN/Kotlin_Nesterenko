package com.gb.kotlin_1728_2_1

class Person constructor(var name: String, var age: Int = 28, var bio: String= "") {
    init {
        name = "initName"
        age = 21
    }
}