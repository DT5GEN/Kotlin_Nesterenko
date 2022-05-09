package com.gb.kotlin_1728_2_1.kotlin

fun main(){
    testWhen(1)
    testWhen(13)
    testWhen(3)
    testWhen("3444")
    testWhen(3.343)
    testWhen(0.435F)
}

fun testWhen(input: Any) {
    when(input){
        1-> println("Edinica")
        2-> println("Dva")
        3 -> println("Troko")
        in 10..20 -> println("Число  $input попало в диапазон от 10 до 20")
        is String -> println(" Введена такая строка '${input}' длиной ${input.length} символа ")
        else -> println("Что-то ещё ввели")
    }
}
