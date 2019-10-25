package com.vunhiem.drinkwater.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.utils.AppConfig
import hiennguyen.me.circleseekbar.CircleSeekBar
import kotlinx.android.synthetic.main.activity_drinkwater_main.*
import kotlinx.android.synthetic.main.dialog_home.*
import kotlinx.android.synthetic.main.fragment_drink_water.*
import me.itangqi.waveloadingview.WaveLoadingView
import java.text.SimpleDateFormat
import java.util.*


class DrinkWaterFragment : Fragment() {

    lateinit var tgBtn100: ToggleButton
    lateinit var tgBtn200: ToggleButton
    lateinit var tgBtn300: ToggleButton
    lateinit var tgBtn400: ToggleButton
    lateinit var circle: CircleSeekBar
    //    var circle: Int = 0
//    lateinit var circlee: CircularSliderRange
    lateinit var tvWater: TextView
    lateinit var img: ImageView
    lateinit var tvGold: TextView
    lateinit var tvLastDrink: TextView
    lateinit var waveView:WaveLoadingView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(com.vunhiem.drinkwater.R.layout.fragment_drink_water, container, false)
        tvWater = view.findViewById(com.vunhiem.drinkwater.R.id.tv_water_drank)
        circle = view.findViewById(com.vunhiem.drinkwater.R.id.circular)
//        circle = context?.let { AppConfig.getWaterLevel(it) }!!
        img = view.findViewById(com.vunhiem.drinkwater.R.id.img_last_drink)
        tvGold = view.findViewById(com.vunhiem.drinkwater.R.id.tv_daily_gold)
        waveView = view.findViewById(R.id.waveView)
        tgBtn100 = view.findViewById(R.id.tg_btn_100)
        tgBtn200 = view.findViewById(R.id.tg_btn_200)
        tgBtn300 = view.findViewById(R.id.tgbtn_300)
        tgBtn400 = view.findViewById(R.id.tgbtn_400)
        tvLastDrink = view.findViewById(R.id.tv_time_last)
        loadData()
        loadDialog()
        return view
    }

    override fun onStop() {
        super.onStop()
        waveView.cancelAnimation()
        waveView.visibility = View.INVISIBLE

    }

    override fun onStart() {
        super.onStart()
        waveView.startAnimation()
        waveView.visibility = View.VISIBLE

    }

    private fun loadDialog() {
        var dialog = context?.let { AppConfig.getSettingIcon(it) }
        if (dialog == true) {
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_home, null)


            val mBuilder = context?.let {
                AlertDialog.Builder(it)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()
            mAlertDialog.window!!.setGravity(Gravity.BOTTOM)
            val layoutParams = mAlertDialog.window!!.attributes

            layoutParams.y = 300 // bottom margin
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            context?.let { AppConfig.setSettingIcon(false, it) }
            mAlertDialog.img_remove_dialog_home.setOnClickListener {

                mAlertDialog.dismiss()
            }

        }

    }


    private fun loadData() {
        var x = this!!.context?.let { AppConfig.getGoldDrink(it) }
        var lastTime = this!!.context?.let { AppConfig.getTimeDrink(it) }
        tvGold.text = "Daily gold: $x ml"

        tvLastDrink.text = "Last drink: $lastTime"
        tvWater.text = context?.let { AppConfig.getWaterLevel(it).toString() }
        var waterLevel = context?.let { AppConfig.getWaterLevel(it) }

        var waterDrink2 = AppConfig.getWaterLevel(context!!)!!.toInt()
        var waterDrink = AppConfig.getWaterLevel(context!!)!!.toFloat()
        var waterGold = AppConfig.getGoldDrink(context!!)
        val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
        var waterCompletion = (waterDrink / waterGold2) * 100
        var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
//        var x = circle.currentProgress
        circle.setProgressDisplayAndInvalidate(waterCompletion2!!)




        circle.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                return true
            }

        })
//        if (waterLevel == 0) {
//            circlee.setEndAngle((-90).toDouble())
//        } else {
//            var y = Math.round(waterGold)
//            if (circle < y / 8) {
//                circlee.setEndAngle((-15).toDouble())
//            } else if (circle < y / 4) {
//                circlee.setEndAngle((0).toDouble())
//            } else if (circle < 3 * y / 8) {
//                circlee.setEndAngle((50).toDouble())
//            } else if (circle < y / 2) {
//                circlee.setEndAngle((70).toDouble())
//            } else if (circle < 5 * y / 8) {
//                circlee.setEndAngle((90).toDouble())
//            } else if (circle < 3 * y / 4) {
//                circlee.setEndAngle((140).toDouble())
//            } else if (circle < 7 * y / 8) {
//                circlee.setEndAngle((200).toDouble())
//            } else if (circle > 7 * y / 8 && circle < y) {
//                circlee.setEndAngle((250).toDouble())
//            } else if (circle > y || circle == y) {
//                circlee.setEndAngle((269).toDouble())
//            }
//        }


    }


    internal var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "textChange") {
                //            Toast.makeText(context, "SMS Recieved", Toast.LENGTH_SHORT).show();
                val text = intent.getStringExtra("text")
                tvWater.text = text
//                circle = Integer.parseInt(tvWater.text.toString())
                val c = Calendar.getInstance()
                val sdf = SimpleDateFormat("HH:mm")
                val currentDate = sdf.format(c.getTime())
                tvLastDrink.text = "Last drink: $currentDate"
                AppConfig.setTimeDrink("$currentDate", context)
                val now = Calendar.getInstance()

                var time = context?.let { AppConfig.getIntervalTime(it) }
                val timeinterval = time!!.replace("[^\\d.]".toRegex(), "").toInt()

                now.add(Calendar.MINUTE, timeinterval)
                val intervaltime = sdf.format(now.getTime())
                Log.i("jjjj", "$intervaltime")
                AppConfig.setTimeNextDrink(intervaltime, context)
                var waterLevel = context?.let { AppConfig.getWaterLevel(it) }
                var waterDrink = AppConfig.getWaterLevel(context!!)!!.toFloat()
                var waterGold = AppConfig.getGoldDrink(context!!)
                val waterGold2 = waterGold!!.replace("[.]".toRegex(), "").toFloat()
                var waterCompletion = (waterDrink / waterGold2) * 100
                var waterCompletion2 = Math.round(waterCompletion.toDouble()).toInt()
                circle.setProgressDisplayAndInvalidate(waterCompletion2!!)


            }
        }
    }


    override fun onResume() {
        super.onResume()
        loadData()
        context!!.registerReceiver(receiver, IntentFilter("textChange"))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        check()
        loadData()
        getText()
        onClick()


    }

    private fun check() {
        var water = AppConfig.getWaterup(context!!)
        if (water == 100){
            tg_btn_100.isChecked = true
            tg_btn_200.isChecked = false
            tgbtn_300.isChecked = false
            tgbtn_400.isChecked = false
            val intent2 = Intent()
            intent2.action = "imgChange"
            intent2.putExtra("img", 1)
            context!!.sendBroadcast(intent2)
        }
        else if (water == 200){
            tg_btn_100.isChecked = false
            tg_btn_200.isChecked = true
            tgbtn_300.isChecked = false
            tgbtn_400.isChecked = false
            val intent2 = Intent()
            intent2.action = "imgChange"
            intent2.putExtra("img", 2)
            context!!.sendBroadcast(intent2)
        } else if (water == 300){
            tg_btn_100.isChecked = false
            tg_btn_200.isChecked = false
            tgbtn_300.isChecked = true
            tgbtn_400.isChecked = false
            val intent2 = Intent()
            intent2.action = "imgChange"
            intent2.putExtra("img", 3)
            context!!.sendBroadcast(intent2)
            }
        else{
            tg_btn_100.isChecked = false
            tg_btn_200.isChecked = false
            tgbtn_300.isChecked = false
            tgbtn_400.isChecked = true
            val intent2 = Intent()
            intent2.action = "imgChange"
            intent2.putExtra("img", 4)
            context!!.sendBroadcast(intent2)
        }
    }


    private fun onClick() {
        tg_btn_100.setOnClickListener {
            tg_btn_100.isChecked = true
            tg_btn_200.isChecked = false
            tgbtn_300.isChecked = false
            tgbtn_400.isChecked = false
            context?.let { it1 ->
                AppConfig.setWaterup(100, it1)
                val intent2 = Intent()
                intent2.action = "imgChange"
                intent2.putExtra("img", 1)
                context!!.sendBroadcast(intent2)
            }
        }
        tg_btn_200.setOnClickListener {
            tg_btn_100.isChecked = false
            tg_btn_200.isChecked = true
            tgbtn_300.isChecked = false
            tgbtn_400.isChecked = false
            context?.let { it1 ->
                AppConfig.setWaterup(200, it1)
                val intent2 = Intent()
                intent2.action = "imgChange"
                intent2.putExtra("img", 2)
                context!!.sendBroadcast(intent2)
            }
        }
        tgbtn_300.setOnClickListener {

            tg_btn_100.isChecked = false
            tg_btn_200.isChecked = false
            tgbtn_300.isChecked = true
            tgbtn_400.isChecked = false
            context?.let { it1 ->
                AppConfig.setWaterup(300, it1)
                val intent2 = Intent()
                intent2.action = "imgChange"
                intent2.putExtra("img", 3)
                context!!.sendBroadcast(intent2)
            }
        }
        tgbtn_400.setOnClickListener {
            tg_btn_100.isChecked = false
            tg_btn_200.isChecked = false
            tgbtn_300.isChecked = false
            tgbtn_400.isChecked = true
            context?.let { it1 ->
                AppConfig.setWaterup(400, it1)
                val intent2 = Intent()
                intent2.action = "imgChange"
                intent2.putExtra("img", 4)
                context!!.sendBroadcast(intent2)
            }
        }
    }

    fun getText(): String {
        return tv_water_drank.text.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(receiver)
    }
}
