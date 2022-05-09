package com.gb.kotlin_1728_2_1.kotlin


//Task 1
// Даны два массива : [1,2,3,2,0] [5,1,2,7,3,2]
//Нужно вернуть пересечения [1,2,2,3] (порядок неважен)

fun main() {

    println(
        getRepeatedIntersection(
            intArrayOf(1, 4, 2, 3, 2, 0, 4),
            intArrayOf(5, 1, 2, 7, 3, 2, 4, 4)
        )
    )

    println(countLetters("AAAABBBCCXYZDDDDFFFFFFFFFFFEEEEAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB"))

    println(groupWords(arrayOf("eat", "tea", "tan", "ate", "nat", "bat", "bta", "gle")))

}


//Task 2
// Посчитать и вывести из строки  буквы
// "AAAABBBCCXYZDDDDFFFFFFFFFFFEEEEAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBB"

fun getRepeatedIntersection(a1: IntArray, a2: IntArray): List<Int> {
    val s1 = a1.toHashSet()
    val s2 = a2.toHashSet()

    val result = mutableListOf<Int>()

    for (el in s1) {
        if (s2.contains(el)) {
            val numOfRepeats = minOf(a1.count { it == el }, a2.count { it == el })
            repeat(numOfRepeats) { result.add(el) }
        }
    }
    return result
}

fun countLetters(str: String): String {
    var currentLetter = str[0]
    var count = 1
    var result = ""

    for (letter in str.substring(1))
        if (currentLetter == letter) {
            count++
        } else {
            if (count == 1)
                result += currentLetter
            else
                result += "$currentLetter$count"
            count = 1
            currentLetter = letter
        }

    // обработаем последние буквы в строке
    if (count == 1)
        result += currentLetter
    else result += "$currentLetter$count"
    return result
}

//Task 3

/**
 * Sample Input ["eat", "tea", "tan", "ate", "nat", "bat"]
 * Sample Output [ ["ate", "tea", "eat"], ["nat", "tan"], ["bat"] ]
 * Сгруппировать по "общим буквам"
 * https://www.youtube.com/watch?v=30tchn0TjaM
 */

fun groupWords (words: Array<String>): List<List<String>>{
    val result: MutableList<List<String>> = mutableListOf()

    val map = mutableMapOf<String, MutableList<String>>()

    for (word in words){
        val sortedWord = word.toCharArray().sorted().joinToString ("")
        if (map.containsKey(sortedWord))
            map[sortedWord]?.add(word)
        else
            map[sortedWord] = mutableListOf(word)
    }
    for ((key, value) in map) {
        result.add(value)
    }

    return result
}