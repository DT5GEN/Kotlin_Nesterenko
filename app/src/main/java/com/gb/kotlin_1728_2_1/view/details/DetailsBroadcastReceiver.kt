package com.gb.kotlin_1728_2_1.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DetailsBroadcastReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("mylogs", "onReceive() ${intent?.action} ${intent?.getStringExtra(MAIN_SERVICE_KEY_EXTRAS)}")
    }
}