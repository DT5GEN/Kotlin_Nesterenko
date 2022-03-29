package com.gb.kotlin_1728_2_1.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.ActivityMainBinding
import com.gb.kotlin_1728_2_1.lesson6.MyBroadcastReceiver
import com.gb.kotlin_1728_2_1.model.WeatherDTO
import com.gb.kotlin_1728_2_1.model.utils.BUNDLE_KEY
import com.gb.kotlin_1728_2_1.model.utils.BUNDLE_KEY_WEATHER
import com.gb.kotlin_1728_2_1.view.details.DetailsFragment
import com.gb.kotlin_1728_2_1.view.history.HistoryFragment
import com.gb.kotlin_1728_2_1.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val receiver = MyBroadcastReceiver()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.getParcelableExtra<WeatherDTO>(BUNDLE_KEY_WEATHER) != null) {  // TODO разобраться что тут
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.container,
                    DetailsFragment.newInstance(
                        Bundle().apply {
                            putParcelable(
                                BUNDLE_KEY, intent.getParcelableExtra<WeatherDTO>(
                                    BUNDLE_KEY_WEATHER
                                )
                            )
                        }
                    ))
                .addToBackStack("").commit()
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commit()
        }

        val sharedPref =
            getSharedPreferences("", Context.MODE_PRIVATE) // на уровне приложения по ТЕГу

        val activityPref = getPreferences(Context.MODE_PRIVATE)  // работает на уровне активити

        val appPreference = getDefaultSharedPreferences(this)

        appPreference.getString("key", "")

        val editor = appPreference.edit().putString("key", "valueAny")
        editor.putString("key", "valueAny")
        editor.putString("key2", "valueAny")
        editor.putInt("key3", 777)
        editor.putBoolean("key4", false)
        editor.apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return return when (item.itemId) {
            R.id.menu_history -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, HistoryFragment.newInstance())
                    .addToBackStack("")
                    .commit()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}



