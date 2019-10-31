package com.vunhiem.drinkwater.service

import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log

import androidx.annotation.RequiresApi
import com.vunhiem.drinkwater.Model.WaterMonth
import com.vunhiem.drinkwater.Model.WaterWeek
import com.vunhiem.drinkwater.Model.Weight
import com.vunhiem.drinkwater.db.DBHelper
import com.vunhiem.drinkwater.utils.AppConfig
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
internal class YourJobService : JobService() {
    lateinit var db:DBHelper
    override fun onStartJob(params: JobParameters): Boolean {

        db = DBHelper(this)
        AppConfig.setWaterlevel(0, this)
        Log.i("test26", "0h00 JobService")
        AppConfig.setCount(0, this)
        AppConfig.setCheckFull(false, this)
        db.deleteHistory()

        val handler3 = Handler()
        handler3.postDelayed({
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            if (day == Calendar.MONDAY) {
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
            val c = Calendar.getInstance()
            val sdf2 = SimpleDateFormat("dd")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            if (currentDate2 == 1) {
                for (i in 1..31) {
                    db.updateWaterMonth(WaterMonth(i, 0, 0, 0))
                    db.updateWeightMonth(Weight(i, 0f))
                }
            }
            Log.i("0h00", "resetall")

        }, 62000)









        return false
    }

    override fun onStopJob(params: JobParameters): Boolean {
        return false
    }

    companion object {
        private val JOB_ID = 1
        private val ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L // 1 Day
        private val ONE_WEEK_INTERVAL = 7 * 24 * 60 * 60 * 1000L // 1 Week

        fun schedule(context: Context, intervalMillis: Long) {
            val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val componentName = ComponentName(context, YourJobService::class.java)
            val builder = JobInfo.Builder(JOB_ID, componentName)
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            builder.setPeriodic(intervalMillis)
            jobScheduler.schedule(builder.build())
        }

        fun cancel(context: Context) {
            val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(JOB_ID)
        }
    }
}
