package com.gb.kotlin_1728_2_1

open class Person constructor(val name: String, var age: Int = 28, var bio: String = "") {

    var newProperty: String = "word"
    get() {

        return "$field hack York"
    }

    set(str: String) {
        field = "$str hack Set"
    }

    fun foo(name: String): Int {
        return age
    }


}

class Student(var group: Int, name: String, age: Int) : Person(name, age)
 {

}

fun foo (name: String) = name
