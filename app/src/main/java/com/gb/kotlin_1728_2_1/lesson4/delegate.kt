package com.gb.kotlin_1728_2_1.lesson4

interface Base1 {
    fun someFun1()
}

interface Base2 {
    fun someFun2()
}

class BaseImpl1() : Base1 {
    override fun someFun1() {
        println(" Fun 1")
    }
}


class BaseImpl2() : Base2 {
    override fun someFun2() {
        println(" Fun 2")
    }
}

class LazyImpl(base1: Base1, base2: Base2) : Base1 by base1, Base2 by base2 { // Lazy паттерн
    // есть интерфейс, есть класс который его реализует. И передаём через конструкцию LazyImpl(base1: Base1, base2: Base2) : Base1 by base1
}

fun main() {
    val base1 = BaseImpl1()
    //base1.someFun1()
    val base2 = BaseImpl2()
    //  base2.someFun2()
    val boss = LazyImpl(base1, base2)
    boss.someFun1()
    boss.someFun2()
}