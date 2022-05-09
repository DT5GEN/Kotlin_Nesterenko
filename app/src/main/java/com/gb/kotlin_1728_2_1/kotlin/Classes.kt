package com.gb.kotlin_1728_2_1.kotlin

import com.bumptech.glide.Glide.init

fun main() {
    val man1 = Person("Boris", "Jonson", 55)
    val baby = Person ("Billy", "Jonson", 1)
    val man2 = Person ("Jen", "Obama", 35, baby)

    println(man1.lastName)

    val o1 = Rectangle(23.0, 22.0)
    val o2 = Rectangle(23.0, 22.0)

    println(o1 == o2) // должно быть true
}

// Primary Constructor - первичный конструктор
class Person(val firstName: String, val lastName: String, var age: Int) {
    var children: MutableList<Person> = mutableListOf() // в java обычный ArrayList

    init {
        println(" Person -> $firstName is created ")
    }

    constructor(firstName: String, lastName: String, age: Int, child: Person) :
            this(
        firstName,
        lastName,
        age
    ) {
children.add(child)

    }
 // конструктор без аргументов
    constructor() : this( "Default", "Defaultov", 100)

}

data class Rectangle(var height: Double, var length: Double) {
    var perimeter = (height + length) * 2
    var squad = (height*length)

    init{
        println(" Периметр равен $perimeter см. ")
        println(" Площадь равна  $squad  см.кв. ")
    }

    var test = 1
    get() = field + 1  // field как в java в геттере this
    set(value) {
        if (value < 0) println(" Negative value ")
        field = value
         println( " Positive value " )
    }

    fun area () = height * length

}