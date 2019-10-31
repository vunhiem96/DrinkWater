package com.vunhiem.drinkwater.screen.dailywater

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vunhiem.drinkwater.screen.dailywatermain.DrinkwaterMainActivity
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.screen.dailywatermain.BedReceiver
import com.vunhiem.drinkwater.screen.dailywatermain.WakeUpReceiver
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_daily_water.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class DailyWaterActivity : AppCompatActivity() {
    private var alarmMgr3: AlarmManager? = null
    private lateinit var alarmIntent3: PendingIntent

    private var alarmMgr4: AlarmManager? = null
    private lateinit var alarmIntent4: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_water)
        loadData()

        btn_dailywater.setOnClickListener {
            AppConfig.setHistoryLogin(true, this)
            AppConfig.setGoldDrink(tv_water_need_drink.text.toString(),this@DailyWaterActivity)
            alarmWakeBed()
            val intent = Intent(this, DrinkwaterMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadData() {
        var x = AppConfig.getWeight(this@DailyWaterActivity)
        val y1 = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
        val y = y1.toInt()
        var ml = y/0.03
        val twoDForm = DecimalFormat("#")
        var xx = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
        tv_water_need_drink.text="$xx"



    }
    fun alarmWakeBed(){
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


            alarmMgr3 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent3 = Intent(this, BedReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(this, 2, intent, 0)
            }
            val calendar3: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hoursBed)
                set(Calendar.MINUTE, minutesBed)
                set(Calendar.SECOND,0)
                set(Calendar.MILLISECOND,0)
            }
            alarmMgr3?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar3.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent3
            )

            alarmMgr4 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent4 = Intent(this, WakeUpReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(this, 3, intent, 0)
            }
            val calendar4: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hoursWake)
                set(Calendar.MINUTE, minutesWake)
                set(Calendar.SECOND,0)
                set(Calendar.MILLISECOND,0)
            }
            alarmMgr4?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar4.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent4
            )



    }
}
