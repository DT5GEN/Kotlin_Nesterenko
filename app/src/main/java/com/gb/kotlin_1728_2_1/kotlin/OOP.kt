package com.gb.kotlin_1728_2_1.kotlin

data class Player(val name:String, val playerClass: String, var dex : Int = 1, var str:Int = 2, var lvl: Int = 0,
var maxHP:Int = 10, var nextLvlXP:Int =10, var XP : Int = 0, var HP:Int = 10
             ){

    fun increaseXP(xp:Int){
        XP +=xp
        if (XP >= nextLvlXP){
            lvlUP()
        }
        println(" Опыт игрока теперь равен $XP ")
        //println(this.toString())
    }

    private fun lvlUP() {
        lvl +=1
        nextLvlXP *=2

        str +=1
        dex +=2

        maxHP = (maxHP * 1.2).toInt()
        HP = maxHP
        println(" Уровень игрока теперь равен $lvl , а максимальное здоровье достигло $maxHP НР !! ")
    }
}

fun main () {
    var hero = Player("Maga", "Magistr")

    for (i in 0 .. 5 ) hero.increaseXP( 12)
}