package com.gb.kotlin_1728_2_1.lesson4

import android.util.Log

class LambdaKotlin {

    interface OnMyLisener {
        fun onSomething(string: String)
    }

    var mOnMyLisener: OnMyLisener? = null
    fun setOnMyListener(l: OnMyLisener?){
        mOnMyLisener = l
    }

    fun  main(){
      val l= anyText@  {  // благодаря указателю ххх@ появляется возможность в лямбде иметь несколько точек выхода (return xxx@)

          Log.d("my logs", "run 1")
          if (false)
      return@anyText 2334234
          else return@anyText 555
      }
        Log.d("my logs", "${l()} run 2")

        anotherFun(lambdaFun, anonimFun)
        val l3 = {
            param1:String, param2:Int, param3:Float->
            2+3
        }

        mOnMyLisener?.onSomething("Стринга")

        l3("", 34, 2f)





        val textLambda = lambdaFun(54)
        val textAnonim = anonimFun (1)

    }

    fun anotherFun(bla1:(i:Int)-> String, blabla:(i:Int)-> String){
        Log.d("my log", "anotherFun() called with")
        Log.d("my log", "anotherFun() called with")
    }

    val  anonimFun = fun (i:Int):String{
        Log.d("my log", " anonimFun $i")
        return "22"
    }

    val lambdaFun =  {i: Int -> // лямбда не умеет получать явный указатель на возврвщаемый тип, компилятор сам этот тип выводит. И лямбла не может выходить сама из себя через ретёрн
        Log.d("my log", " lambdaFun $i")
        "45"
    }



}