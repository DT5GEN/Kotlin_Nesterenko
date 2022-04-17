package com.gb.kotlin_1728_2_1.view

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.gb.kotlin_1728_2_1.R
import com.gb.kotlin_1728_2_1.databinding.ActivityMainBinding
import com.gb.kotlin_1728_2_1.lesson10.MapsFragment
import com.gb.kotlin_1728_2_1.lesson6.MyBroadcastReceiver
import com.gb.kotlin_1728_2_1.lesson9.ContentProviderFragment
import com.gb.kotlin_1728_2_1.model.WeatherDTO
import com.gb.kotlin_1728_2_1.model.utils.BUNDLE_KEY
import com.gb.kotlin_1728_2_1.model.utils.BUNDLE_KEY_WEATHER
import com.gb.kotlin_1728_2_1.view.details.DetailsFragment
import com.gb.kotlin_1728_2_1.view.history.HistoryFragment
import com.gb.kotlin_1728_2_1.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val receiver = MyBroadcastReceiver()

    companion object {
        private const val NOTIFICATION_ID_1 = 1
        private const val NOTIFICATION_ID_2 = 2
        private const val CHANNEL_ID_1 = "Известия "
        private const val CHANNEL_ID_2 = "Котлин покажет. "
    }

    private fun pushNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder_1 = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .apply {
                setSmallIcon(R.drawable.ic_kotlin_logo)
                setContentTitle("Заголовок для $CHANNEL_ID_1")
                setContentText("Сообщение для $CHANNEL_ID_1")
                priority = NotificationCompat.PRIORITY_MAX
            }
        val notificationBuilder_2 = NotificationCompat.Builder(this, CHANNEL_ID_2)
            .apply {
                setSmallIcon(R.drawable.ic_kotlin_logo)
                setContentTitle("Заголовок для $CHANNEL_ID_2")
                setContentText("Сообщение для $CHANNEL_ID_2")
                priority = NotificationCompat.PRIORITY_MAX // Если не указать, будет дефолтное значение
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName_1 = "Name for $CHANNEL_ID_1"
            val channelDescription_1 = "Descriptoin $CHANNEL_ID_1"
            val channelPriority =
                NotificationManager.IMPORTANCE_HIGH  // не ставить приоритет MAX, так как это только для системных уведомлений работает
            val channel_1 = NotificationChannel(
                CHANNEL_ID_1,
                channelName_1,
                channelPriority
            ).apply { description = channelDescription_1 }
            notificationManager.createNotificationChannel(channel_1)


        }
notificationManager.notify(NOTIFICATION_ID_1, notificationBuilder_1.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName_2 = "Name for $CHANNEL_ID_2"
            val channelDescription_2 = "Descriptoin $CHANNEL_ID_1"
            val channelPriority =
                NotificationManager.IMPORTANCE_HIGH  // не ставить приоритет MAX, так как это только для системных уведомлений работает
            val channel_2 = NotificationChannel(
                CHANNEL_ID_2,
                channelName_2,
                channelPriority
            ).apply { description = channelDescription_2 }
                notificationManager.createNotificationChannel(channel_2)
             //notificationManager.deleteNotificationChannel(CHANNEL_ID_2) // удаление 2-го канала уведомлений

        }

notificationManager.notify(NOTIFICATION_ID_2, notificationBuilder_2.build())
notificationManager.notify(NOTIFICATION_ID_2+1, notificationBuilder_2.build())
notificationManager.notify(NOTIFICATION_ID_2+2, notificationBuilder_2.build())
notificationManager.notify(NOTIFICATION_ID_2+3, notificationBuilder_2.build())
notificationManager.notify(NOTIFICATION_ID_2+4, notificationBuilder_2.build())
notificationManager.notify(NOTIFICATION_ID_2+5, notificationBuilder_2.build())

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pushNotification() // вызываем созданные уведомления
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance()).commit()
        }

        Build.VERSION_CODES.O // так можно узнать версию андроид O

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
            R.id.menu_content -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, ContentProviderFragment.newInstance())
                    .addToBackStack("")
                    .commit()
                true
            }
            R.id.menu_google_maps -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, MapsFragment())
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



