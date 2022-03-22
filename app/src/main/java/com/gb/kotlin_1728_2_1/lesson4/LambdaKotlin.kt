package com.gb.kotlin_1728_2_1.lesson4

import android.util.Log

class LambdaKotlin {
    data class Person(var name: String, var age: Int)

    fun main() {

        var p: Person? = null

        if (p != null) {

            Log.d("mlg", "${p.name} ${p.age}")
        }

        p?.let {
            p = null
            Log.d("mlg", "${it.name} ${it.age}")
        }


        p?.run {
            Log.d("mlg", "${name} ${age}")
        }

        val examplePerson = Person("Vuasya", 19)

        // with()
        val resultWith = with(examplePerson) {
            /*
            если хотим работать с большим объектом у которого очень много полей,
            и при этом не важно возвращать изменения этого объекта ( не нужно его дублировать )
            или вовсе не нужен результат, а интересует именно работа с объектом person.
            Удобнее работать именно c with()
             */
            name = "vasya"
            age = 34

        }


        // .let  и  run - возвращают значения лямбды внутри, вызывают для того чтобы поработать с ресивером (run) или параметром (let). И возвращают последнюю строку лямбды
        examplePerson.let { }
        // .run
        examplePerson.run { }
        run {
            // run - это отдельная область видимости
            val person2 = Person("Petr", 23)
            val examplePerson = Person("Petr", 23)

            /*
            * при создании person2 за этим блоком нет никакого конфликта, мы делаем что-то внутри
            * и это не пересекается с внешней областью
             */

        }
        val person2 = Person("Petr", 23)


        // .also
        val personAlso =  examplePerson.also {
            /*
            нельзя опустить it так как внутрь лямбды значение попадает в качестве параметра
             */
            it.age = 12
            it.name = "Petr"
        }


        // .apply   И also возвращают обьект Person
       val personApply = examplePerson.apply {
        /*
        this можно опускать
         */
            this.name = " Ivan "
            name = " Ivan "
        }

    }

}