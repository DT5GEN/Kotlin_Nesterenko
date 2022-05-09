package com.gb.kotlin_1728_2_1.kotlin

import androidx.core.graphics.component1
import androidx.core.graphics.component2

fun main() {
    val a = listOf(1, 2, 7, 3, 5)
    a.forEach { e -> println(e) }
    println(a.map { e -> e * 2 })
    println(a.filter { e -> e % 2 == 1 })
    println(a.reduce { sum, i -> sum + i }) // проссумирует все элементы списка как и sum()
    println(a.sum())


    println(a.sorted())
    println(a.sortedByDescending { it })
    a.any { it > 10 } // будет false
    a.all { it < 10 } // true
    a.sum()

    val numbers = listOf(1,3,-4,5,2, -22)
    val (positive, negative) = numbers.partition { it > 0 }
    val (z,x) = 23
    println(z + 4)
    println(x + 8)
    println( "  Положительные ${positive}")
    println( "  Отрицательные ${negative}")

   val result = listOf("a","b", "c", "ba", "ccc", "ad").groupBy { it.length }
    println(result)



}