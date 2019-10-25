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
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.service.DrinkWaterService
import com.vunhiem.drinkwater.utils.AppConfig
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    var check: Boolean? = false
    private val NOTIFICATION_ID = 144
    val CHANNEL_ID = "1"
    private var notification2: Notification? = null
    private val NOTIFICATION_ID2 = 145
    val CHANNEL_ID2 = "2"
    override fun onReceive(context: Context, intent: Intent) {
        var reminder = AppConfig.getReminder(context)
        var checkbed = AppConfig.getCheckBed(context)
        if (reminder == true && checkbed==true) {
            Log.i("vkl2", "vkl2")
            val i = Intent(context, DrinkwaterMainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            i.putExtra("hihi", true)
            val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
            val intent = PendingIntent.getActivity(
                context, uniqueInt, i,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val remoteViews = RemoteViews(context.packageName, R.layout.noti_layout)
            var check2 = AppConfig.getCheckfull(context)
            if (check2 == true) {
                remoteViews.setTextViewText(R.id.tv_title, "You drank enough water today")
            }
            val CHANNEL_ID = "1"
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                val builder = NotificationCompat.Builder(context)
                    .setSmallIcon(com.vunhiem.drinkwater.R.drawable.ic_water)
                    .setContentIntent(intent)
                    .setAutoCancel(true)
                val notification = builder.build()
                notification.contentView = remoteViews
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notification.bigContentView = remoteViews
                }
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm a")
                val currentDate = sdf.format(c.getTime())
                remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                notification.defaults = Notification.DEFAULT_LIGHTS


//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                notification.bigContentView = remoteViews
//            }

//            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                NotificationManagerCompat.from(context).notify(1000, notification)
            } else {
                val name = "Drink Water_channel"
                val descriptionText = "A cool channel"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }

                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(com.vunhiem.drinkwater.R.drawable.ic_water)
                    .setContentIntent(intent)
                    .setAutoCancel(true)
                    .setSound(null)


                val notification = builder.build()
                notification.contentView = remoteViews
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notification.bigContentView = remoteViews
                }
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm a")
                val currentDate = sdf.format(c.getTime())
                remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                notification.defaults = Notification.DEFAULT_LIGHTS

//            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                NotificationManagerCompat.from(context).notify(1000, notification)
            }



        }
    }
}