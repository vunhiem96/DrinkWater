package com.vunhiem.drinkwater.screen.dailywatermain

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.vunhiem.drinkwater.fragment.DrinkWaterFragment
import com.vunhiem.drinkwater.fragment.HistoryFragment
import com.vunhiem.drinkwater.fragment.SettingFragment
import com.vunhiem.drinkwater.fragment.WeightFragment
import com.vunhiem.drinkwater.service.DrinkWaterService
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_drinkwater_main.*
import kotlinx.android.synthetic.main.dialog_exit.*
import java.text.SimpleDateFormat
import java.util.*
import com.vunhiem.drinkwater.R
import kotlinx.android.synthetic.main.dialog_after_enought_water.*
import kotlinx.android.synthetic.main.dialog_enought_water.*


class DrinkwaterMainActivity : AppCompatActivity() {
    var mp:MediaPlayer? = null
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private var alarmMgr2: AlarmManager? = null
    private lateinit var alarmIntent2: PendingIntent

    private var alarmMgr3: AlarmManager? = null
    private lateinit var alarmIntent3: PendingIntent

    private var alarmMgr4: AlarmManager? = null
    private lateinit var alarmIntent4: PendingIntent

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.vunhiem.drinkwater.R.layout.activity_drinkwater_main)
        check()
        onClick()
        val fragment = DrinkWaterFragment()
        addFragment(fragment, "DrinkWaterFragment")
        startWaterService()
        addWater()


    }

    private fun check() {
        var waterup = AppConfig.getWaterup(this)
        if (waterup == 100) {
            img_100.setImageResource(R.drawable.ic_100)
        } else if (waterup == 200) {
            img_100.setImageResource(R.drawable.twoplus)
        } else if (waterup == 300) {
            img_100.setImageResource(R.drawable.threeplus)
        } else if (waterup == 400) {
            img_100.setImageResource(R.drawable.fourplus)
        }


    }

    private fun startWaterService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(Intent(this, DrinkWaterService::class.java))
        } else {
            this.startService(Intent(this, DrinkWaterService::class.java))
        }

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
        if(mp!=null) {
            if (mp!!.isPlaying) {
                mp!!.stop()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun addWater() {
        img_add.setOnClickListener {
            if(mp!=null) {
                if (mp!!.isPlaying) {
                    mp!!.stop()
                }
            }
            mp = MediaPlayer.create(this, R.raw.dinkwater)
            mp!!.start()

            var main = tgbtn_waterdaily.isChecked
            if(main == false){
                tgbtn_history.isChecked = false
                tgbtn_waterdaily.isChecked = true
                tgbtn_weight.isChecked = false
                tgbtn_setting.isChecked = false
//            img_100.setImageResource(R.drawable.ic_100)
//            AppConfig.setWaterup(100,this)
                val fragment = DrinkWaterFragment()
                addFragment(fragment, "DrinkWaterFragment")
            }


            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("HH:mm")
            val currentDate = sdf.format(c.getTime())
            AppConfig.setTimeDrink("$currentDate", this)


            val now = Calendar.getInstance()
            var time = AppConfig.getIntervalTime(this)
            val timeinterval = time!!.replace("[^\\d.]".toRegex(), "").toInt()

            var x = now.add(Calendar.MINUTE, timeinterval)
            val intervaltime = sdf.format(now.getTime())
            AppConfig.setTimeNextDrink(intervaltime, this)


            var count = AppConfig.getCount(this)
            AppConfig.setCount(count!! + 1, this)
            var waterlevel = AppConfig.getWaterLevel(this)
            var waterUP: Int? = AppConfig.getWaterup(this@DrinkwaterMainActivity)?.let { it1 ->
                waterlevel?.plus(
                    it1
                )
            }
            AppConfig.setWaterlevel(waterUP!!, this)
            var waterup = AppConfig.getWaterup(this)
            val intent1 = Intent()
            intent1.action = "textChange"
            intent1.putExtra("text", "$waterUP")
            intent1.putExtra("water", "$waterup")
            AppConfig.setWaterup2(waterup!!.toInt(), this)
            this.sendBroadcast(intent1)
            var waterGold = AppConfig.getGoldDrink(this)
            val y = waterGold!!.replace("[.]".toRegex(), "").toInt()
            if (waterUP < y) {
                var waterup = AppConfig.getWaterup(this)
                if (waterup == 100) {
                    Toast.makeText(this, "You have just drunk 100ml water", Toast.LENGTH_LONG)
                        .show()
                }
                if (waterup == 200) {
                    Toast.makeText(this, "You have just drunk 200ml water", Toast.LENGTH_LONG)
                        .show()
                }
                if (waterup == 300) {
                    Toast.makeText(this, "You have just drunk 300ml water", Toast.LENGTH_LONG)
                        .show()
                }
                if (waterup == 400) {
                    Toast.makeText(this, "You have just drunk 400ml water", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                var checkFull = AppConfig.getCheckfull(this)
                if (checkFull == false) {
                    AppConfig.setCheckFull(true, this)

                    val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_enought_water, null)
                    var img: ImageView = mDialogView.findViewById(R.id.img_dialog_enought)
                    val mBuilder = AlertDialog.Builder(this)
                        .setView(mDialogView)
                    val mAlertDialog = mBuilder.show()
                    Glide.with(this).load(R.drawable.img_enought_water).into(img)
                    mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    mAlertDialog.img_remove_enought.setOnClickListener {
                        mAlertDialog.dismiss()

                    }


                }
               else {

                    val mDialogView = LayoutInflater.from(this)
                        .inflate(R.layout.dialog_after_enought_water, null)
                    var img: ImageView = mDialogView.findViewById(R.id.img_dialog_enought)
                    val mBuilder = AlertDialog.Builder(this)
                        .setView(mDialogView)
                    val mAlertDialog = mBuilder.show()
                    Glide.with(this).load(R.drawable.img_after_enought).into(img)
                    mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    mAlertDialog.img_remove__after_enought.setOnClickListener {
                        mAlertDialog.dismiss()
                    }

                }
            }



            AppConfig.setRecord(true, this)

            val sdf2 = SimpleDateFormat("HH:mm")// here set the pattern as you date in string was containing like date/month/year
            var bed = AppConfig.getBedTime(this)
            var wakeUp = AppConfig.getWakeUp(this)
            val d = sdf2.parse(bed)
            val d2 = sdf2.parse((wakeUp))
            val cal = Calendar.getInstance()
            cal.time = d
            val hoursBed = cal.get(Calendar.HOUR_OF_DAY)
            val minutesBed = cal.get(Calendar.MINUTE)
            Log.i("bed","$hoursBed")
            Log.i("bed","$minutesBed")
            cal.time = d2
            val hoursWake = cal.get(Calendar.HOUR_OF_DAY)
            val minutesWake = cal.get(Calendar.MINUTE)
            Log.i("bed","$hoursWake")
            Log.i("bed","$minutesWake")

            val xxx = SystemClock.elapsedRealtime() + (timeinterval * 60 * 1000)
            val xxx2 = (timeinterval * 60 * 1000)
            Log.i("vkl2", "$xxx")
            Log.i("vkl2", "$xxx2")

            alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(this, 0, intent, 0)
            }
//            ((timeinterval * 60 * 1000)-10000).toLong()
//            alarmMgr?.setExact(
//                AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + (timeinterval * 60 * 1000)-10000,
//                alarmIntent
//            )
//            SystemClock.elapsedRealtime() + (timeinterval * 60 * 1000)-10000
            var reminder = AppConfig.getReminder(this)
            if (reminder == true) {
                alarmMgr?.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + (timeinterval * 60 * 1000)-(900*timeinterval),
                    ((timeinterval* 60 * 1000)-(900*timeinterval)).toLong(),
                    alarmIntent
                )
            }

            alarmMgr2 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent2 = Intent(this, AlarmReceiver2::class.java).let { intent ->
                PendingIntent.getBroadcast(this, 1, intent, 0)
            }
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 55)
            }
            alarmMgr2?.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmIntent2
            )
            alarmMgr3?.cancel(alarmIntent3)
            alarmMgr3 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent3 = Intent(this, BedReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(this, 2, intent, 0)
            }
            val calendar3: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hoursBed)
                set(Calendar.MINUTE, minutesBed)
            }
            alarmMgr3?.set(
                AlarmManager.ELAPSED_REALTIME,
                calendar3.timeInMillis,
                alarmIntent3
            )
            alarmMgr4?.cancel(alarmIntent4)
            alarmMgr4 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent4 = Intent(this, WakeUpReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(this, 3, intent, 0)
            }
            val calendar4: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hoursWake)
                set(Calendar.MINUTE, minutesWake)
            }
            alarmMgr4?.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                calendar4.timeInMillis,
                alarmIntent4
            )

        }


    }


    private fun onClick() {
        tgbtn_waterdaily.setOnClickListener {
            tgbtn_history.isChecked = false
            tgbtn_waterdaily.isChecked = true
            tgbtn_weight.isChecked = false
            tgbtn_setting.isChecked = false
//            img_100.setImageResource(R.drawable.ic_100)
//            AppConfig.setWaterup(100,this)
            val fragment = DrinkWaterFragment()
            addFragment(fragment, "DrinkWaterFragment")

        }
        tgbtn_weight.setOnClickListener {
            tgbtn_history.isChecked = false
            tgbtn_waterdaily.isChecked = false
            tgbtn_weight.isChecked = true
            tgbtn_setting.isChecked = false
            val fragmentxx = WeightFragment()
            addFragment(fragmentxx, "WeightFragment")
        }

        tgbtn_setting.setOnClickListener {
            tgbtn_history.isChecked = false
            tgbtn_waterdaily.isChecked = false
            tgbtn_weight.isChecked = false
            tgbtn_setting.isChecked = true
            val fragmentx = SettingFragment()
            addFragment(fragmentx, "SettingFragment")
        }
        tgbtn_history.setOnClickListener {
            tgbtn_history.isChecked = true
            tgbtn_waterdaily.isChecked = false
            tgbtn_weight.isChecked = false
            tgbtn_setting.isChecked = false
            val fragmentyy = HistoryFragment()
            addFragment(fragmentyy, "HistoryFragment")


        }
    }

    private fun addFragment(fragment: Fragment, name: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                com.vunhiem.drinkwater.R.id.container,
                fragment,
                fragment.javaClass.getSimpleName()
            )
            .commit()
    }

    override fun onBackPressed() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mAlertDialog.btn_yes.setOnClickListener {
            finish()
        }
        mAlertDialog.btn_no.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    override fun onResume() {

        super.onResume()
        this.registerReceiver(receiver3, IntentFilter("imgChange"))
    }

    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(receiver3)
    }

    internal var receiver3: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "imgChange") {
                var img = intent.getIntExtra("img", 1)
                if (img == 1) {
                    img_100.setImageResource(R.drawable.ic_100)
                } else if (img == 2) {
                    img_100.setImageResource(R.drawable.twoplus)
                } else if (img == 3) {
                    img_100.setImageResource(R.drawable.threeplus)
                } else if (img == 4) {
                    img_100.setImageResource(R.drawable.fourplus)
                }

            }
        }
    }

}


