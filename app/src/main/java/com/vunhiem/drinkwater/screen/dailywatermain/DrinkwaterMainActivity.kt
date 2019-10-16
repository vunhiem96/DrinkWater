package com.vunhiem.drinkwater.screen.dailywatermain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.vunhiem.drinkwater.R
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
import androidx.core.content.ContextCompat.startForegroundService
import android.os.Build
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class DrinkwaterMainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.vunhiem.drinkwater.R.layout.activity_drinkwater_main)
        img_100.setImageResource(R.drawable.ic_100)
        onClick()
        val fragment = DrinkWaterFragment()
        addFragment(fragment, "DrinkWaterFragment")
        startWaterService()
        addWater()

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

    private fun addWater() {
        img_add.setOnClickListener {
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("HH:mm")
            val currentDate = sdf.format(c.getTime())
            AppConfig.setTimeDrink("$currentDate", this)


            val now = Calendar.getInstance()

            var time = AppConfig.getIntervalTime(this)
            val timeinterval = time!!.replace("[^\\d.]".toRegex(), "").toInt()

            now.add(Calendar.MINUTE, timeinterval)
            val intervaltime = sdf.format(now.getTime())
            AppConfig.setTimeNextDrink(intervaltime,this)

            var count = AppConfig.getCount(this)
            AppConfig.setCount(count!! +1,this)
            var waterlevel = AppConfig.getWaterLevel(this)
            var waterUP: Int? = AppConfig.getWaterup(this@DrinkwaterMainActivity)?.let { it1 ->
                waterlevel?.plus(
                    it1
                )
            }
            AppConfig.setWaterlevel(waterUP!!, this)
            val intent1 = Intent()
            intent1.action = "textChange"
            intent1.putExtra("text", "$waterUP")
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
                AppConfig.setCheckFull(true,this)
                Toast.makeText(
                    this,
                    "You've been drinking excessively for today",
                    Toast.LENGTH_LONG
                ).show()
            }
            AppConfig.setRecord(true,this)
        }

    }

    private fun onClick() {
        tgbtn_waterdaily.setOnClickListener {
            tgbtn_history.isChecked = false
            tgbtn_waterdaily.isChecked = true
            tgbtn_weight.isChecked = false
            tgbtn_setting.isChecked = false
            img_100.setImageResource(R.drawable.ic_100)
            AppConfig.setWaterup(100,this)
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


