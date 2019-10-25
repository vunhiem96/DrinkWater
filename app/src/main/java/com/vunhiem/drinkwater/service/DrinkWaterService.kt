package com.vunhiem.drinkwater.service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RemoteViews
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vunhiem.drinkwater.Model.WaterMonth
import com.vunhiem.drinkwater.Model.WaterWeek
import com.vunhiem.drinkwater.Model.Weight
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper
import com.vunhiem.drinkwater.screen.dailywatermain.DrinkwaterMainActivity
import com.vunhiem.drinkwater.utils.AppConfig
import java.text.SimpleDateFormat
import java.util.*


class DrinkWaterService : Service() {
    var check: Boolean? = false
    internal lateinit var db: DBHelper
    private val NOTIFICATION_ID = 144
    val CHANNEL_ID = "1"
    private var notification2: Notification? = null
    private val NOTIFICATION_ID2 = 145
    val CHANNEL_ID2 = "2"
    override fun onCreate() {
        super.onCreate()
        applicationContext!!.registerReceiver(receiverService, IntentFilter("resetWater"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("notificationWater"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("resetall"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterMon"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterTue"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterWed"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterFri"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterSar"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterSun"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("addWaterThu"))
        applicationContext!!.registerReceiver(receiverService, IntentFilter("resetallMonth"))


        CreateChanel()
        reSet()
        CreateNotification()
        dataBase()
    }

    private fun CreateChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNotificationChannel()
            val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID2)
                .setSmallIcon(R.drawable.ic_noti2)
                .setContentTitle("DrinkWater active")
                .setOnlyAlertOnce(true)
                .setSound(null)
            notification2 = builder.build()
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(
                    NOTIFICATION_ID2,
                    notification2!!
                )
            }
            startForeground(NOTIFICATION_ID2, notification2)

        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Drink Water_channel"
            val descriptionText = "A cool channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID2, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun dataBase() {
        db = DBHelper(applicationContext)
        db.createDefaultNotesIfNeed()
        db.createDefaultNotesIfNeed2()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY


    }

    fun reSet() {
        val h = Handler()
        h.post(
            object : Runnable {
                override fun run() {
                    val c = Calendar.getInstance()
                    val sdf = SimpleDateFormat("HH:mm")
                    val currentDate = sdf.format(c.getTime())
                    val sdf2 = SimpleDateFormat("dd")
                    val currentDate2 = sdf2.format(c.getTime()).toInt()
                    System.out.println(" C DATE is  " + currentDate)
                    var y = "$currentDate"
                    if (y == "00:00") {
                        AppConfig.setCheckFull(false, applicationContext)
                        val intent = Intent()
                        intent.action = "resetWater"
                        applicationContext!!.sendBroadcast(intent)
                    }
                    val calendar = Calendar.getInstance()
                    val day = calendar.get(Calendar.DAY_OF_WEEK)

                    if (y == "23:59" && day == Calendar.MONDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterMon"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "00:00" && day == Calendar.MONDAY) {
                        val intent3 = Intent()
                        intent3.action = "resetall"
                        applicationContext!!.sendBroadcast(intent3)
                    }
                    if (y == "23:59" && day == Calendar.TUESDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterTue"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:59" && day == Calendar.WEDNESDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterWed"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:59" && day == Calendar.THURSDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterThu"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:59" && day == Calendar.FRIDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterFri"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:59" && day == Calendar.SATURDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterSar"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (y == "23:59" && day == Calendar.SUNDAY) {
                        val intent1 = Intent()
                        intent1.action = "addWaterSun"
                        applicationContext!!.sendBroadcast(intent1)
                    }
                    if (currentDate2 == 1 && y == "00:00") {
                        val intent1 = Intent()
                        intent1.action = "resetallMonth"
                        applicationContext!!.sendBroadcast(intent1)
                    }

                    h.postDelayed(this, 20000)
                }
            })
    }


    internal var receiverService: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent) {
            if (intent.action == "resetWater") {
                AppConfig.setWaterlevel(0, applicationContext)
                Log.i("xxxcccc", "onReciver")
                AppConfig.setCount(0, applicationContext)
                AppConfig.setCheckFull(false, applicationContext)
//                CreateChanel()
            }
            if (intent.action == "notificationWater") {

//                val mDialogView = LayoutInflater.from(applicationContext)
//                    .inflate(com.vunhiem.drinkwater.R.layout.dialog_time_drinkwater, null)
//                val mBuilder = AlertDialog.Builder(applicationContext)
//                    .setView(mDialogView)
//                val mAlertDialog = mBuilder!!.show()
//                mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                var btnAccept: Button =
//                    mDialogView.findViewById(com.vunhiem.drinkwater.R.id.btn_accept_service)
//                btnAccept.setOnClickListener {
//                    val intent = Intent(applicationContext, DrinkwaterMainActivity::class.java)
//                    startActivity(intent)
//                    mAlertDialog.dismiss()
//                }


                var check3 = AppConfig.getCheckBed(applicationContext)
                var check2 = AppConfig.getCheckfull(applicationContext)
                val remoteViews = RemoteViews(packageName, R.layout.noti_layout)
                if (check == true) {
                    remoteViews.setTextViewText(R.id.tv_title, "Drink water to start a fresh day")
                    check = false
                } else if (check2 == true) {
                    remoteViews.setTextViewText(R.id.tv_title, "You drank enough water today")
                } else {
                    remoteViews.setTextViewText(R.id.tv_title, "It's time to drink water")
                }
                val i = Intent(applicationContext, DrinkwaterMainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                i.putExtra("hihi", true)
                val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
                val intent = PendingIntent.getActivity(
                    applicationContext, uniqueInt, i,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                val CHANNEL_ID = "1"
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
                    val builder = NotificationCompat.Builder(applicationContext)
                        .setSmallIcon(com.vunhiem.drinkwater.R.drawable.ic_water)
                        .setContentIntent(intent)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setOngoing(true)
                    val notification = builder.build()
                    notification.contentView = remoteViews
                    var mode = AppConfig.getRemiderMode(applicationContext)
                    Log.i("chiaa", "$mode")

                    if (mode == 2) {

                        notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
                        notification.defaults =
                            notification.defaults or Notification.DEFAULT_VIBRATE


                    } else if (mode == 1) {

                        notification.defaults = Notification.DEFAULT_VIBRATE
                        notification.defaults =
                            notification.defaults or Notification.DEFAULT_VIBRATE
                    } else if (mode == 0) {
                        notification.defaults = Notification.DEFAULT_LIGHTS
                    }



                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        notification.bigContentView = remoteViews
                    }
                    val c = Calendar.getInstance()
                    val sdf = SimpleDateFormat("HH:mm a")
                    val currentDate = sdf.format(c.getTime())
                    remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                    NotificationManagerCompat.from(applicationContext).notify(1000, notification)
                } else {
                    val name = "Drink Water_channel"
                    val descriptionText = "A cool channel"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                        description = descriptionText
                    }

                    val notificationManager: NotificationManager =
                        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                    val CHANNEL_ID = "1"
                    val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                        .setSmallIcon(com.vunhiem.drinkwater.R.drawable.ic_water)
                        .setOnlyAlertOnce(true)
                        .setContentIntent(intent)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setOngoing(true)
//                        .setVibrate(longArrayOf(2000))

                    val notification = builder.build()
//                    with(NotificationManagerCompat.from(applicationContext)) {
//                        notify(
//                            NOTIFICATION_ID,
//                            notification!!
//                        )
//                    }

                    notification.contentView = remoteViews
                    var mode = AppConfig.getRemiderMode(applicationContext)
                    Log.i("chiaa", "$mode")

                    if (mode == 2) {
                        notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
                        notification.defaults =
                            notification.defaults or Notification.DEFAULT_VIBRATE
                    } else if (mode == 1) {
                        notification.defaults = Notification.DEFAULT_VIBRATE
                        notification.defaults =
                            notification.defaults or Notification.DEFAULT_VIBRATE
                    } else if (mode == 0) {
                        notification.defaults = Notification.DEFAULT_LIGHTS
                    }



                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        notification.bigContentView = remoteViews
                    }
                    val c = Calendar.getInstance()
                    val sdf = SimpleDateFormat("HH:mm a")
                    val currentDate = sdf.format(c.getTime())
                    remoteViews.setTextViewText(R.id.tv_time_noti, currentDate)
                    NotificationManagerCompat.from(applicationContext).notify(1000, notification)
                }


            }
            if (intent.action == "addWaterMon") {
                var waterDrink2 = AppConfig.getWaterLevel(applicationContext)!!.toInt()
                var waterDrink = AppConfig.getWaterLevel(applicationContext)!!.toFloat()
                var waterGold = AppConfig.getGoldDrink(applicationContext)
                val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
                var waterCompletion = (waterDrink / waterGold2) * 100
                var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
                var count = AppConfig.getCount(applicationContext)
                val water = WaterWeek(2, waterDrink2, waterCompletion2, count!!)
                db.updateWaterWeek(water)
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("dd")
                val currentDate = sdf.format(c.getTime()).toInt()
                val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
                db.updateWaterMonth(waterMonth)
            }
            if (intent.action == "addWaterTue") {
                var waterDrink2 = AppConfig.getWaterLevel(applicationContext)!!.toInt()
                var waterDrink = AppConfig.getWaterLevel(applicationContext)!!.toFloat()
                var waterGold = AppConfig.getGoldDrink(applicationContext)
                val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
                var waterCompletion = (waterDrink / waterGold2) * 100
                var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
                var count = AppConfig.getCount(applicationContext)
                val water = WaterWeek(3, waterDrink2, waterCompletion2, count!!)
                db.updateWaterWeek(water)
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("dd")
                val currentDate = sdf.format(c.getTime()).toInt()
                val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
                db.updateWaterMonth(waterMonth)
            }
            if (intent.action == "addWaterWed") {
                var waterDrink2 = AppConfig.getWaterLevel(applicationContext)!!.toInt()
                var waterDrink = AppConfig.getWaterLevel(applicationContext)!!.toFloat()
                var waterGold = AppConfig.getGoldDrink(applicationContext)
                val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
                var waterCompletion = (waterDrink / waterGold2) * 100
                var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
                var count = AppConfig.getCount(applicationContext)
                val water = WaterWeek(4, waterDrink2, waterCompletion2, count!!)
                db.updateWaterWeek(water)
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("dd")
                val currentDate = sdf.format(c.getTime()).toInt()
                val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
                db.updateWaterMonth(waterMonth)
            }
            if (intent.action == "addWaterThu") {
                var waterDrink2 = AppConfig.getWaterLevel(applicationContext)!!.toInt()
                var waterDrink = AppConfig.getWaterLevel(applicationContext)!!.toFloat()
                var waterGold = AppConfig.getGoldDrink(applicationContext)
                val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
                var waterCompletion = (waterDrink / waterGold2) * 100
                var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
                var count = AppConfig.getCount(applicationContext)
                val water = WaterWeek(5, waterDrink2, waterCompletion2, count!!)
                db.updateWaterWeek(water)
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("dd")
                val currentDate = sdf.format(c.getTime()).toInt()
                val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
                db.updateWaterMonth(waterMonth)
            }
            if (intent.action == "addWaterFri") {
                var waterDrink2 = AppConfig.getWaterLevel(applicationContext)!!.toInt()
                var waterDrink = AppConfig.getWaterLevel(applicationContext)!!.toFloat()
                var waterGold = AppConfig.getGoldDrink(applicationContext)
                val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
                var waterCompletion = (waterDrink / waterGold2) * 100
                var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
                var count = AppConfig.getCount(applicationContext)
                Log.i("djdjdj", "$waterCompletion")
                Log.i("djdjdj", "$waterCompletion2")
                Log.i("djdjdj", "$waterGold2")
                Log.i("djdjdj", "$waterDrink")

                val water = WaterWeek(6, waterDrink2, waterCompletion2, count!!)
                db.updateWaterWeek(water)
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("dd")
                val currentDate = sdf.format(c.getTime()).toInt()
                val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
                db.updateWaterMonth(waterMonth)
            }
            if (intent.action == "addWaterSar") {
                var waterDrink2 = AppConfig.getWaterLevel(applicationContext)!!.toInt()
                var waterDrink = AppConfig.getWaterLevel(applicationContext)!!.toFloat()
                var waterGold = AppConfig.getGoldDrink(applicationContext)
                val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
                var waterCompletion = (waterDrink / waterGold2) * 100
                var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
                var count = AppConfig.getCount(applicationContext)
                val water = WaterWeek(7, waterDrink2, waterCompletion2, count!!)
                db.updateWaterWeek(water)
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("dd")
                val currentDate = sdf.format(c.getTime()).toInt()
                val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
                db.updateWaterMonth(waterMonth)
            }
            if (intent.action == "addWaterSun") {
                var waterDrink2 = AppConfig.getWaterLevel(applicationContext)!!.toInt()
                var waterDrink = AppConfig.getWaterLevel(applicationContext)!!.toFloat()
                var waterGold = AppConfig.getGoldDrink(applicationContext)
                val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
                var waterCompletion = (waterDrink / waterGold2) * 100
                var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
                var count = AppConfig.getCount(applicationContext)
                val water = WaterWeek(8, waterDrink2, waterCompletion2, count!!)
                db.updateWaterWeek(water)
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("dd")
                val currentDate = sdf.format(c.getTime()).toInt()
                val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
                db.updateWaterMonth(waterMonth)
            }
            if (intent.action == "resetall") {
                val mon = WaterWeek(2, 0, 0, 0)
                val tue = WaterWeek(3, 0, 0, 0)
                val wed = WaterWeek(4, 0, 0, 0)
                val thu = WaterWeek(5, 0, 0, 0)
                val fri = WaterWeek(6, 0, 0, 0)
                val sar = WaterWeek(7, 0, 0, 0)
                val sun = WaterWeek(8, 0, 0, 0)
                db.updateWaterWeek(mon)
                db.updateWaterWeek(tue)
                db.updateWaterWeek(thu)
                db.updateWaterWeek(fri)
                db.updateWaterWeek(wed)
                db.updateWaterWeek(sar)
                db.updateWaterWeek(sun)

                val monw = Weight(2, 0f)
                val tuew = Weight(3, 0f)
                val wedw = Weight(4, 0f)
                val thuw = Weight(5, 0f)
                val friw = Weight(6, 0f)
                val sarw = Weight(7, 0f)
                val sunw = Weight(8, 0f)
                db.updateWeightWeek(monw)
                db.updateWeightWeek(tuew)
                db.updateWeightWeek(wedw)
                db.updateWeightWeek(thuw)
                db.updateWeightWeek(friw)
                db.updateWeightWeek(sarw)
                db.updateWeightWeek(sunw)

            }
            if (intent.action == "resetallMonth") {
                for (i in 1..31) {
                    db.updateWaterMonth(WaterMonth(i, 0, 0, 0))
                    db.updateWeightMonth(Weight(i, 0f))
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.unregisterReceiver(receiverService)
    }

    fun CreateNotification() {
        var checkbed = AppConfig.getCheckBed(applicationContext)
        val h = Handler()
        h.post(
            object : Runnable {
                override fun run() {
                    var timeNextDrink = AppConfig.getTimeNextDrink(applicationContext)
                    val c = Calendar.getInstance()
                    val sdf = SimpleDateFormat("HH:mm")
                    val currentDate = sdf.format(c.getTime())
                    var y = "$currentDate"
                    var timeWakeup = AppConfig.getWakeUp(applicationContext)
                    var reminder = AppConfig.getReminder(applicationContext)
                    var bedTime = AppConfig.getBedTime(applicationContext)
                    if (y == bedTime) {
                        AppConfig.setCheckBed(false, applicationContext)
                    }
                    var check3 = AppConfig.getCheckBed(applicationContext)
                    if (reminder == true) {
                        if (checkbed == true) {
                            if (y == timeNextDrink && check3 == true) {
                                val intent = Intent()
                                intent.action = "notificationWater"
                                applicationContext!!.sendBroadcast(intent)
                            } else if (y == timeWakeup) {
                                AppConfig.setCheckBed(true, applicationContext)
                                check = true
                                val intent = Intent()
                                intent.action = "notificationWater"
                                applicationContext!!.sendBroadcast(intent)

                            }
                        }
                    }
                    h.postDelayed(this, 29000)
                }
            })
    }

}





