package com.gb.kotlin_1728_2_1.lesson4

interface Base1{
    fun someFun1()
}
interface Base2{
    fun someFun2()
}

class BaseImpl1():Base1{
    override fun someFun1() {
        TODO("Not yet implemented")
    }
}


class BaseImpl2():Base2{
    override fun someFun2() {
        TODO("Not yet implemented")
    }
}

class LazyImpl(worker1: Base1, worker2: Base2):Base1 by worker1, Base2 by worker2{

}

fun main(){
    val base1 = BaseImpl1()
    base1.someFun1()
    val base2 = BaseImpl2()
    base2.someFun2()
    val boss = LazyImpl()
}