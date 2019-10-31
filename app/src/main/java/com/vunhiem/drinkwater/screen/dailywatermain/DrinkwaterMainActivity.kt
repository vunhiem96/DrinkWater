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

import com.vunhiem.drinkwater.Model.WaterHistory
import com.vunhiem.drinkwater.Model.WaterMonth
import com.vunhiem.drinkwater.Model.WaterWeek
import com.vunhiem.drinkwater.Model.Weight
import com.vunhiem.drinkwater.service.DrinkWaterService
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_drinkwater_main.*
import kotlinx.android.synthetic.main.dialog_exit.*
import java.text.SimpleDateFormat
import java.util.*
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper
import com.vunhiem.drinkwater.fragment.*
import com.vunhiem.drinkwater.service.YourJobService
import kotlinx.android.synthetic.main.dialog_after_enought_water.*
import kotlinx.android.synthetic.main.dialog_enought_water.*


class DrinkwaterMainActivity : AppCompatActivity(),DrinkWater {
//   lateinit var mInterstitialAd:InterstitialAd
//    var listHistory: ArrayList<WaterHistory> = ArrayList()
    override fun drinkWater(check: Boolean) {
        if(check==true){
            onClickButtonDrink()
        }
    }

    lateinit var db:DBHelper
    var mp:MediaPlayer? = null
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private var alarmMgr0h00: AlarmManager? = null
    private lateinit var alarmIntent0h00: PendingIntent

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.vunhiem.drinkwater.R.layout.activity_drinkwater_main)
        AppConfig.setWaterup(100, this)
        check()
        updateDataChart()
        dataBase()
        onClick()
        val fragment = DrinkWaterFragment()
        addFragment(fragment, "DrinkWaterFragment")
        startWaterService()
        addWater()
//        googleAds()
        jobService()


    }

    private fun jobService() {

        alarmMgr0h00 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent0h00 = Intent(this, NewDayReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 5, intent, 0)
        }
        val calendar2: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 0)


        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr0h00?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar2.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent0h00
            )
        }

        YourJobService.schedule(this, calendar2.timeInMillis)
    }

//    private fun googleAds() {
//        mInterstitialAd = InterstitialAd(this)
//
//    }

    private fun updateDataChart() {
        db = DBHelper(this)
        val c = Calendar.getInstance()
            val sdf2 = SimpleDateFormat("dd")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            if (currentDate2 == 1) {
                for (i in 2..31) {
                    db.updateWaterMonth(WaterMonth(i, 0, 0, 0))
                    db.updateWeightMonth(Weight(i, 0f))
                }
            }

        val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            if (day == Calendar.MONDAY) {
                val tue = WaterWeek(3, 0, 0, 0)
                val wed = WaterWeek(4, 0, 0, 0)
                val thu = WaterWeek(5, 0, 0, 0)
                val fri = WaterWeek(6, 0, 0, 0)
                val sar = WaterWeek(7, 0, 0, 0)
                val sun = WaterWeek(8, 0, 0, 0)
                db.updateWaterWeek(tue)
                db.updateWaterWeek(thu)
                db.updateWaterWeek(fri)
                db.updateWaterWeek(wed)
                db.updateWaterWeek(sar)
                db.updateWaterWeek(sun)

                val tuew = Weight(3, 0f)
                val wedw = Weight(4, 0f)
                val thuw = Weight(5, 0f)
                val friw = Weight(6, 0f)
                val sarw = Weight(7, 0f)
                val sunw = Weight(8, 0f)

                db.updateWeightWeek(tuew)
                db.updateWeightWeek(wedw)
                db.updateWeightWeek(thuw)
                db.updateWeightWeek(friw)
                db.updateWeightWeek(sarw)
                db.updateWeightWeek(sunw)
            }
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
        val intent = Intent(this, YourJobService::class.java)
        startService(intent)
    }

    override fun onStart() {
        updateDataChart()
        dataBase()
        super.onStart()

    }
    private fun dataBase() {
        db.createDefaultWeightWeek()
        db.createDefaultWeightMonth()
        db.createDefaultWeightYear()
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        var weight = AppConfig.getWeight(this)
        var weightAdd = weight!!.replace("[^\\d.]".toRegex(), "").toFloat()
        if(day == Calendar.MONDAY){
            db.updateWeightWeek(Weight(2, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.TUESDAY){
            db.updateWeightWeek(Weight(3, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.WEDNESDAY){
            db.updateWeightWeek(Weight(4, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.THURSDAY){
            db.updateWeightWeek(Weight(5, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.FRIDAY){
            db.updateWeightWeek(Weight(6, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.SATURDAY){
            db.updateWeightWeek(Weight(7, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()

            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            Log.i("vcvcff","$currentDate2")
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.SUNDAY){
            db.updateWeightWeek(Weight(8, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
    }

    override fun onStop() {
        super.onStop()
        if(mp!=null) {
            if (mp!!.isPlaying) {
                mp!!.stop()
            }
        }

    }
    fun onClickButtonDrink(){
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
        var histoty= WaterHistory(1,"$currentDate",waterup)
        db.addHistoty(histoty)
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

//            listHistory.add(WaterHistory("$currentDate",waterup))



        }



        AppConfig.setRecord(true, this)

//            val sdf2 = SimpleDateFormat("HH:mm")// here set the pattern as you date in string was containing like date/month/year
//            var bed = AppConfig.getBedTime(this)
//            var wakeUp = AppConfig.getWakeUp(this)
//            val d = sdf2.parse(bed)
//            val d2 = sdf2.parse((wakeUp))
//            val cal = Calendar.getInstance()
//            cal.time = d
//            val hoursBed = cal.get(Calendar.HOUR_OF_DAY)
//            val minutesBed = cal.get(Calendar.MINUTE)
//            Log.i("bed","$hoursBed")
//            Log.i("bed","$minutesBed")
//            cal.time = d2
//            val hoursWake = cal.get(Calendar.HOUR_OF_DAY)
//            val minutesWake = cal.get(Calendar.MINUTE)
//            Log.i("bed","$hoursWake")
//            Log.i("bed","$minutesWake")

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
            alarmMgr?.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + (timeinterval * 60 * 1000)-(900*timeinterval),
                ((timeinterval* 60 * 1000)-(900*timeinterval)).toLong(),
                alarmIntent
            )
        }


//            alarmMgr3?.cancel(alarmIntent3)
//            alarmMgr3 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmIntent3 = Intent(this, BedReceiver::class.java).let { intent ->
//                PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT)
//            }
//            val calendar3: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, hoursBed)
//                set(Calendar.MINUTE, minutesBed)
//            }
//            alarmMgr3?.set(
//                AlarmManager.RTC_WAKEUP,
//                calendar3.timeInMillis,
//                alarmIntent3
//            )
//            alarmMgr4?.cancel(alarmIntent4)
//            alarmMgr4 = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmIntent4 = Intent(this, WakeUpReceiver::class.java).let { intent ->
//                PendingIntent.getBroadcast(this, 3, intent, PendingIntent.FLAG_CANCEL_CURRENT)
//            }
//            val calendar4: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, hoursWake)
//                set(Calendar.MINUTE, minutesWake)
//            }
//            alarmMgr4?.set(
//                AlarmManager.RTC_WAKEUP,
//                calendar4.timeInMillis,
//                alarmIntent4
//            )

        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        if (day == Calendar.MONDAY) {
            Log.i("test26", "monday")
            var waterDrink2 = AppConfig.getWaterLevel(this)!!.toInt()
            var waterDrink = AppConfig.getWaterLevel(this)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(this)
            val water = WaterWeek(2, waterDrink2, waterCompletion2, count!!)
            db.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            db.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.TUESDAY) {
            Log.i("test26", "tue")
            var waterDrink2 = AppConfig.getWaterLevel(this)!!.toInt()
            var waterDrink = AppConfig.getWaterLevel(this)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(this)
            val water = WaterWeek(3, waterDrink2, waterCompletion2, count!!)
            db.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            db.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.WEDNESDAY) {
            Log.i("test26", "wed")
            var waterDrink2 = AppConfig.getWaterLevel(this)!!.toInt()
            var waterDrink = AppConfig.getWaterLevel(this)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(this)
            val water = WaterWeek(4, waterDrink2, waterCompletion2, count!!)
            db.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            db.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.THURSDAY) {
            Log.i("test26", "thur")
            var waterDrink2 = AppConfig.getWaterLevel(this)!!.toInt()
            var waterDrink = AppConfig.getWaterLevel(this)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(this)
            val water = WaterWeek(5, waterDrink2, waterCompletion2, count!!)
            db.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            db.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.FRIDAY) {
            Log.i("test26", "fri")
            var waterDrink2 = AppConfig.getWaterLevel(this)!!.toInt()
            var waterDrink = AppConfig.getWaterLevel(this)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(this)
            val water = WaterWeek(6, waterDrink2, waterCompletion2, count!!)
            db.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            db.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.SATURDAY) {
            Log.i("test26", "sar")
            var waterDrink2 = AppConfig.getWaterLevel(this)!!.toInt()
            var waterDrink = AppConfig.getWaterLevel(this)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(this)
            val water = WaterWeek(7, waterDrink2, waterCompletion2, count!!)
            db.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            db.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.SUNDAY) {
            Log.i("test26", "sun")
            var waterDrink2 = AppConfig.getWaterLevel(this)!!.toInt()
            var waterDrink = AppConfig.getWaterLevel(this)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(this)
            val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(this)
            val water = WaterWeek(8, waterDrink2, waterCompletion2, count!!)
            db.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(currentDate, waterDrink2, waterCompletion2, count!!)
            db.updateWaterMonth(waterMonth)
        }

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun addWater() {
        img_add.setOnClickListener {
            onClickButtonDrink()

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
//            val bundle = Bundle()
//            bundle.putParcelableArrayList("valuesArray",listHistory)
            val fragmentyy = HistoryFragment()
//            fragmentyy.setArguments(bundle)
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
        this.registerReceiver(receiver4, IntentFilter("adapter"))
    }

    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(receiver3)
        this.unregisterReceiver(receiver4)
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
    internal var receiver4: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if(intent.action =="adapter"){

                var waterUp = intent.getIntExtra("adapterWaterlevel",2)
                Log.i("recycleview","$waterUp")
                if (waterUp == 100) {
                    img_100.setImageResource(R.drawable.ic_100)
                } else if (waterUp == 200) {
                    img_100.setImageResource(R.drawable.twoplus)
                } else if (waterUp == 300) {
                    img_100.setImageResource(R.drawable.threeplus)
                } else if (waterUp == 400) {
                    img_100.setImageResource(R.drawable.fourplus)
                }

            }


        }
    }

}


