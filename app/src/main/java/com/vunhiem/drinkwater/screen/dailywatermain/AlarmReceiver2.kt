package com.vunhiem.drinkwater.screen.dailywatermain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.vunhiem.drinkwater.Model.WaterMonth
import com.vunhiem.drinkwater.Model.WaterWeek
import com.vunhiem.drinkwater.Model.Weight
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper
import com.vunhiem.drinkwater.service.DrinkWaterService
import com.vunhiem.drinkwater.utils.AppConfig
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver2 : BroadcastReceiver() {
    lateinit var db: DBHelper
    var check: Boolean? = false
    private val NOTIFICATION_ID = 144
    val CHANNEL_ID = "1"
    private var notification2: Notification? = null
    private val NOTIFICATION_ID2 = 145
    val CHANNEL_ID2 = "2"
    override fun onReceive(context: Context, intent: Intent) {
//        val ii = Intent(context, DrinkwaterMainActivity::class.java)  //MyActivity can be anything which you want to start on bootup...
//        ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(ii)
//        Log.i("test26", "broascash")
//        db = DBHelper(context)
//        val handler = Handler()
//        handler.postDelayed({
//            AppConfig.setWaterlevel(0, context)
//            Log.i("test26", "onReciver")
//            AppConfig.setCount(0, context)
//            AppConfig.setCheckFull(false, context)
//            val c = Calendar.getInstance()
//            val sdf2 = SimpleDateFormat("dd")
//            val currentDate2 = sdf2.format(c.getTime()).toInt()
//            if (currentDate2 == 1) {
//                for (i in 1..31) {
//                    db.updateWaterMonth(WaterMonth(i, 0, 0, 0))
//                    db.updateWeightMonth(Weight(i, 0f))
//                }
//            }
//
//        }, 300000)

//        val calendar = Calendar.getInstance()
//        val day = calendar.get(Calendar.DAY_OF_WEEK)
//        if (day == Calendar.MONDAY) {
//            Log.i("test26", "monday")
//            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
//            var waterDrink = AppConfig.getWaterLevel(context)!!.toFloat()
//            var waterGold = AppConfig.getGoldDrink(context)
//            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
//            var waterCompletion = (waterDrink / waterGold2) * 100
//            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
//            var count = AppConfig.getCount(context)
//            val water = WaterWeek(2, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterWeek(water)
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("dd")
//            val currentDate = sdf.format(c.getTime()).toInt()
//            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterMonth(waterMonth)
//        }
//        if (day == Calendar.TUESDAY) {
//            Log.i("test26", "tue")
//            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
//            var waterDrink = AppConfig.getWaterLevel(context)!!.toFloat()
//            var waterGold = AppConfig.getGoldDrink(context)
//            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
//            var waterCompletion = (waterDrink / waterGold2) * 100
//            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
//            var count = AppConfig.getCount(context)
//            val water = WaterWeek(3, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterWeek(water)
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("dd")
//            val currentDate = sdf.format(c.getTime()).toInt()
//            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterMonth(waterMonth)
//        }
//        if (day == Calendar.WEDNESDAY) {
//            Log.i("test26", "wed")
//            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
//            var waterDrink = AppConfig.getWaterLevel(context)!!.toFloat()
//            var waterGold = AppConfig.getGoldDrink(context)
//            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
//            var waterCompletion = (waterDrink / waterGold2) * 100
//            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
//            var count = AppConfig.getCount(context)
//            val water = WaterWeek(4, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterWeek(water)
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("dd")
//            val currentDate = sdf.format(c.getTime()).toInt()
//            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterMonth(waterMonth)
//        }
//        if (day == Calendar.THURSDAY) {
//            Log.i("test26", "thur")
//            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
//            var waterDrink = AppConfig.getWaterLevel(context)!!.toFloat()
//            var waterGold = AppConfig.getGoldDrink(context)
//            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
//            var waterCompletion = (waterDrink / waterGold2) * 100
//            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
//            var count = AppConfig.getCount(context)
//            val water = WaterWeek(5, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterWeek(water)
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("dd")
//            val currentDate = sdf.format(c.getTime()).toInt()
//            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterMonth(waterMonth)
//        }
//        if (day == Calendar.FRIDAY) {
//            Log.i("test26", "fri")
//            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
//            var waterDrink = AppConfig.getWaterLevel(context)!!.toFloat()
//            var waterGold = AppConfig.getGoldDrink(context)
//            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
//            var waterCompletion = (waterDrink / waterGold2) * 100
//            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
//            var count = AppConfig.getCount(context)
//            val water = WaterWeek(6, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterWeek(water)
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("dd")
//            val currentDate = sdf.format(c.getTime()).toInt()
//            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterMonth(waterMonth)
//        }
//        if (day == Calendar.SATURDAY) {
//            Log.i("test26", "sar")
//            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
//            var waterDrink = AppConfig.getWaterLevel(context)!!.toFloat()
//            var waterGold = AppConfig.getGoldDrink(context)
//            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
//            var waterCompletion = (waterDrink / waterGold2) * 100
//            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
//            var count = AppConfig.getCount(context)
//            val water = WaterWeek(7, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterWeek(water)
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("dd")
//            val currentDate = sdf.format(c.getTime()).toInt()
//            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterMonth(waterMonth)
//        }
//        if (day == Calendar.SUNDAY) {
//            Log.i("test26", "sun")
//            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
//            var waterDrink = AppConfig.getWaterLevel(context)!!.toFloat()
//            var waterGold = AppConfig.getGoldDrink(context)
//            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
//            var waterCompletion = (waterDrink / waterGold2) * 100
//            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
//            var count = AppConfig.getCount(context)
//            val water = WaterWeek(8, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterWeek(water)
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("dd")
//            val currentDate = sdf.format(c.getTime()).toInt()
//            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
//            db.updateWaterMonth(waterMonth)
//        }


//        val handler3 = Handler()
//        handler3.postDelayed({
//            val calendar = Calendar.getInstance()
//            val day = calendar.get(Calendar.DAY_OF_WEEK)
//            if (day == Calendar.SUNDAY) {
//                val mon = WaterWeek(2, 0, 0, 0)
//                val tue = WaterWeek(3, 0, 0, 0)
//                val wed = WaterWeek(4, 0, 0, 0)
//                val thu = WaterWeek(5, 0, 0, 0)
//                val fri = WaterWeek(6, 0, 0, 0)
//                val sar = WaterWeek(7, 0, 0, 0)
//                val sun = WaterWeek(8, 0, 0, 0)
//                db.updateWaterWeek(mon)
//                db.updateWaterWeek(tue)
//                db.updateWaterWeek(thu)
//                db.updateWaterWeek(fri)
//                db.updateWaterWeek(wed)
//                db.updateWaterWeek(sar)
//                db.updateWaterWeek(sun)
//
//                val monw = Weight(2, 0f)
//                val tuew = Weight(3, 0f)
//                val wedw = Weight(4, 0f)
//                val thuw = Weight(5, 0f)
//                val friw = Weight(6, 0f)
//                val sarw = Weight(7, 0f)
//                val sunw = Weight(8, 0f)
//                db.updateWeightWeek(monw)
//                db.updateWeightWeek(tuew)
//                db.updateWeightWeek(wedw)
//                db.updateWeightWeek(thuw)
//                db.updateWeightWeek(friw)
//                db.updateWeightWeek(sarw)
//                db.updateWeightWeek(sunw)
//            }
//            val c = Calendar.getInstance()
//            val sdf2 = SimpleDateFormat("dd")
//            val currentDate2 = sdf2.format(c.getTime()).toInt()
//            if (currentDate2 == 1) {
//                for (i in 1..31) {
//                    db.updateWaterMonth(WaterMonth(i, 0, 0, 0))
//                    db.updateWeightMonth(Weight(i, 0f))
//                }
//            }
//
//        }, 120000)

//        Log.i("vkl2", "vkl2")
//        val i = Intent(context, DrinkwaterMainActivity::class.java)
//        i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//        i.putExtra("hihi", true)
//        val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
//        val intent = PendingIntent.getActivity(
//            context, uniqueInt, i,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val remoteViews = RemoteViews(context.packageName, R.layout.noti_layout2)
//        val CHANNEL_ID = "1"
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
//            val builder = NotificationCompat.Builder(context)
//                .setSmallIcon(com.vunhiem.drinkwater.R.drawable.ic_water)
//                .setContentIntent(intent)
//                .setAutoCancel(true)
//            val notification = builder.build()
//            notification.contentView = remoteViews
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                notification.bigContentView = remoteViews
//            }
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("HH:mm a")
//            val currentDate = sdf.format(c.getTime())
//            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
//            notification.defaults = Notification.DEFAULT_LIGHTS
//
//
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////                notification.bigContentView = remoteViews
////            }
//
////            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
////            NotificationManagerCompat.from(context).notify(1000, notification)
//        } else {
//            val name = "Drink Water_channel"
//            val descriptionText = "A cool channel"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//
//            val notificationManager: NotificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
////            notificationManager.createNotificationChannel(channel)
//
//            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(com.vunhiem.drinkwater.R.drawable.ic_water)
//                .setContentIntent(intent)
//                .setAutoCancel(true)
//                .setSound(null)
//
//
//            val notification = builder.build()
//            notification.contentView = remoteViews
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                notification.bigContentView = remoteViews
//            }
//            val c = Calendar.getInstance()
//            val sdf = SimpleDateFormat("HH:mm a")
//            val currentDate = sdf.format(c.getTime())
//            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
//            notification.defaults = Notification.DEFAULT_LIGHTS
//
////            remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
////            NotificationManagerCompat.from(context).notify(1000, notification)
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.stopService(Intent(context, DrinkWaterService::class.java))
//            context.startForegroundService(Intent(context, DrinkWaterService::class.java))
//        } else {
////            context.stopService(Intent(context, DrinkWaterService::class.java))
//            context.startService(Intent(context, DrinkWaterService::class.java))
//        }


    }

}