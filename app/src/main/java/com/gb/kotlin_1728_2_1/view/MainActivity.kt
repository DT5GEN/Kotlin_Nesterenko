package com.gb.kotlin_1728_2_1.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.ActivityMainBinding
import com.gb.kotlin_1728_2_1.lesson4.JavaLambda
import com.gb.kotlin_1728_2_1.lesson4.LambdaKotlin
import com.gb.kotlin_1728_2_1.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commit()
        }
        binding.container.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                TODO("Not yet implemented")
            }

        })

        val l = LambdaKotlin()
        l.main()
        l.setOnMyListener(object : LambdaKotlin.OnMyLisener {
            override fun onSomething(string: String) {
                TODO("что-то напишем ))")
            }
        })

//        l.setOnMyListener(callback)
//        l.setOnMyListener(callbackL)  FIXME не принимает люмбду


//        val l = JavaLambda()
//            l.main()
    }

    val callback = object : LambdaKotlin.OnMyLisener { // объект анонимного класса
        override fun onSomething(string: String) {
            TODO("если кто-то передаст ссылку на анонимную функцию")
        }


    }
    val callbackL = { string: String? ->
    }
}



