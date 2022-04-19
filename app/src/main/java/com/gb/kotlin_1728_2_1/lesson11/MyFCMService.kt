package com.gb.kotlin_1728_2_1.lesson11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.gb.kotlin_1728_2_1.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("mylogsFMC", "onNewToken() called with: token = [$token]")
    }

    override fun onMessageReceived(message: RemoteMessage) { // получаем с сервера сообщение
        val data = message.data.toMap()
        if (data.isNotEmpty()) {  // проверяем не пустое ли
            val title = data[KEY_TITLE]  // по ключам добываем значения
            val message = data[KEY_MESSAGE]
            if (!title.isNullOrBlank() && !message.isNullOrBlank())
                pushNotification(title, message)  // и выводим их  в нотификацию
        }

    }


    companion object {
        private const val NOTIFICATION_ID_1 = 1

        private const val CHANNEL_ID_1 = "Известия "
        private const val KEY_TITLE = "myTitle"
        private const val KEY_MESSAGE = "myMessage"

    }

    private fun pushNotification(title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilderOne = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .apply {
                setSmallIcon(R.drawable.ic_kotlin_logo)
                setContentTitle(title)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_MAX
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
        notificationManager.notify(NOTIFICATION_ID_1, notificationBuilderOne.build())

    }


}

// e81rCfLpTPGLhDhE7ckz98:APA91bGrFvzq5LIsIvEWY6LFC6nIwyy5Db0kiQ_3ZnI4WLAsM_CJIvBkF0JsJzePvmWDvL8z5UyDvtBpoefHKv3qt_jImm8A0sBWIFXWOBYqA8JpsFElv7aIBPDfSNM_C78OEtQgqdDk