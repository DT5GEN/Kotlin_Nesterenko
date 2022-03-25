package com.gb.kotlin_1728_2_1.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.ActivityMainBinding
import com.gb.kotlin_1728_2_1.lesson4.main
import com.gb.kotlin_1728_2_1.lesson6.ThreadsFragment
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
                     R.id.menu_threads->{
                         supportFragmentManager.beginTransaction()
                             .add(R.id.container, ThreadsFragment.newInstance())
                             .addToBackStack("").commit()
            true
        } else ->{
             super.onOptionsItemSelected(item)
         }

        }
    }
}



