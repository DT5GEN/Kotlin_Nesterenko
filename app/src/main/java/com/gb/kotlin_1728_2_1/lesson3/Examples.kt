package com.gb.kotlin_1728_2_1.lesson3

import android.util.Log

object Examples : Any() {

    fun main(): Unit {
        // создаём массив
        val phrase: Array<String> = arrayOf(" I ", " hear ", " you ") // обычный метод
//        val phraseOld: Array<String> =
//            Array(3) { "Welcome "; " to "; " KoDlin!" } // ошибочный дедовский метод

        val wordCount = phrase.size

        class Person(val name: String, var age: Int)

        val personUnmutable: List<Person> = listOf(Person("Petya", 12), Person("Igor", 21))
        personUnmutable.get(1) // Igor
        personUnmutable.get(0)  // Petya
         personUnmutable[1] // Igor
        personUnmutable[0]  // Petya
        personUnmutable[1].age = 3


        val personMutable: MutableList<Person> = mutableListOf(Person("Petya", 12), Person("Igor", 21))
        personMutable.get(1) // Igor
        personMutable.get(0)  // Petya
        personMutable[1] // Igor
        personMutable[0]  // Petya
        personMutable[1].age = 3
        personMutable.add(Person("Uasya", 32  ))

        val personUnmutableMutableCopy : MutableList<Person> = personUnmutable.toMutableList() // спарсили неизменяемый лист
        // setOf() -> mutableSetOf()
        // mapOf() -> mutableMapOf()

        val listForExt: List<Int> = listOf(1,2,3,4,5,6,21)
        Log.d("mylog", "${listForExt.filter { it > 3 }}")
        Log.d("mylog", "${listForExt.sorted()}")
        Log.d("mylog", "${listForExt.sorted()}")
        Log.d("mylog", "${listForExt.sorted()}")

    }
}