package com.gb.kotlin_1728_2_1

open class Person constructor(val name: String, var age: Int = 28, var bio: String = "") {

   lateinit var newProperty: String

    fun foo(name: String): Int {
        return age
    }


}

class Student(var group: Int, name: String, age: Int) : Person(name, age)
 {

}

fun foo (name: String) = name
