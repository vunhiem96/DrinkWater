package com.vunhiem.drinkwater.screen.dailywatermain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.service.DrinkWaterService
import com.vunhiem.drinkwater.utils.AppConfig
import java.text.SimpleDateFormat
import java.util.*

class BedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
       AppConfig.setCheckBed(false,context)
        var x = AppConfig.getCheckBed(context)
        Log.i("beddd","$x")
        }
    }
