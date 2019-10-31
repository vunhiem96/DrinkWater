package com.vunhiem.drinkwater.fragment


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vunhiem.drinkwater.Model.WaterHistory
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.adapter.HistoryAdapter
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.fragment_today.*
import kotlin.math.log
import android.content.Intent.getIntent
import com.vunhiem.drinkwater.db.DBHelper


class TodayFragment : Fragment() {
    lateinit var rvHistory:RecyclerView
//    lateinit var tvTimeRecord: TextView
//    lateinit var tvWaterRecord: TextView
    lateinit var tvWaterNext: TextView
    lateinit var tvTimeWaterNext: TextView
    lateinit var rlRecord: RelativeLayout
    lateinit var adapter: HistoryAdapter
    lateinit var db:DBHelper
    var listHistory: ArrayList<WaterHistory> = ArrayList()
    var check: Boolean = false
    var waterLevel= 0
    var waterGold =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_today, container, false)
        // Inflate the layout for this fragment
//        tvTimeRecord = view.findViewById(R.id.tv_time_records)
//        tvWaterRecord = view.findViewById(R.id.tv_water_record)
        tvWaterNext = view.findViewById(R.id.tv_water_next)
        tvTimeWaterNext = view.findViewById(R.id.tv_time_next_drink)
//        rlRecord = view.findViewById(R.id.rl_record)
        rvHistory = view.findViewById(R.id.rv_history)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        db = context?.let { DBHelper(it) }!!
        waterGold = context?.let { AppConfig.getGoldDrink(it) }!!.toInt()
        waterLevel = context?.let { AppConfig.getWaterLevel(it) }!!
        val h = Handler()
        h.post(
            object : Runnable {
                override fun run() {
                    loadData()
                    h.postDelayed(this, 300)

                }
            })

        createRecycleView()
        onClick()

    }

    private fun createRecycleView() {
        listHistory = db.allHistory as ArrayList<WaterHistory>
        val h = Handler()
        h.post(
            object : Runnable {
                override fun run() {
                    listHistory = db.allHistory as ArrayList<WaterHistory>
                    h.postDelayed(this, 300)


                }
            })


        Log.i("listxx","$listHistory")

        rvHistory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        adapter=HistoryAdapter(context!!,listHistory)
        rvHistory.adapter=adapter
        rvHistory.scrollToPosition(listHistory.size - 1)
        adapter.notifyItemInserted(listHistory.size - 1)
    }

    override fun onResume() {
        super.onResume()
        context!!.registerReceiver(receiver2, IntentFilter("textChange2"))
        context!!.registerReceiver(receiver, IntentFilter("textChange"))
        context!!.registerReceiver(receiver3, IntentFilter("adapter"))
    }

    private fun onClick() {
//        rl_record_parent.setOnClickListener {
//            if (check == false) {
//                ln_popup.visibility = View.VISIBLE
//                check = true
//            } else {
//                ln_popup.visibility = View.INVISIBLE
//                check = false
//            }
//        }
//        img_menu.setOnClickListener {
//            if (check == false) {
//                ln_popup.visibility = View.VISIBLE
//                check = true
//            } else {
//                ln_popup.visibility = View.INVISIBLE
//                check = false
//            }
//
//        }
//
//        ln_fragment_history.setOnClickListener {
//            ln_popup.visibility = View.INVISIBLE
//            check = false
//        }
//
//        rl_delete.setOnClickListener {
//            ln_popup.visibility = View.INVISIBLE
//            check = false
//            rl_record.visibility = View.INVISIBLE
//            context?.let { it1 -> AppConfig.setRecord(false, it1) }
//        }
//
//        rl_edit.setOnClickListener {
//
//            var waterUpOld = context?.let { it1 -> AppConfig.getWaterup2(it1) }
//            var cancle: TextView
//            var ok: TextView
//            val rdb100: RadioButton
//            val rdb200: RadioButton
//            val rdb300: RadioButton
//            val rdb400: RadioButton
//            ln_popup.visibility = View.INVISIBLE
//            check = false
//            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_history, null)
//
//            //AlertDialogBuilder
//            val mBuilder = context?.let { it1 ->
//                AlertDialog.Builder(it1)
//                    .setView(mDialogView)
//            }
//
//            val mAlertDialog = mBuilder!!.show()
//            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            rdb100 = mDialogView.findViewById(R.id.rdb_100)
//            rdb200 = mDialogView.findViewById(R.id.rdb_200)
//            rdb300 = mDialogView.findViewById(R.id.rdb_300)
//            rdb400 = mDialogView.findViewById(R.id.rdb_400)
//            if (waterUpOld == 100) {
//                rdb100.isChecked = true
//            } else if (waterUpOld == 200) {
//                rdb200.isChecked = true
//            } else if (waterUpOld == 300) {
//                rdb300.isChecked = true
//            } else if (waterUpOld == 400) {
//                rdb400.isChecked = true
//            }
//            cancle = mDialogView.findViewById(R.id.tv_cancle)
//            ok = mDialogView.findViewById(R.id.tv_ok)
//            cancle.setOnClickListener {
//                mAlertDialog.dismiss()
//            }
//            ok.setOnClickListener {
//                var waterDrank = context?.let { it1 -> AppConfig.getWaterLevel(it1) }
//                var waterNow = waterDrank!! - waterUpOld!!
//                if (rdb100.isChecked == true) {
//                    tvWaterRecord.text = "100 ml"
//                    var waterLevelNow = waterNow + 100
//                    context?.let { it1 -> AppConfig.setWaterlevel(waterLevelNow, it1) }
//                    context?.let { it1 -> AppConfig.setWaterup(100, it1) }
//                    context?.let { it1 -> AppConfig.setWaterup2(100, it1) }
//                } else if (rdb200.isChecked == true) {
//                    tvWaterRecord.text = "200 ml"
//                    var waterLevelNow = waterNow + 200
//                    context?.let { it1 -> AppConfig.setWaterlevel(waterLevelNow, it1) }
//                    context?.let { it1 -> AppConfig.setWaterup(200, it1) }
//                    context?.let { it1 -> AppConfig.setWaterup2(200, it1) }
//                } else if (rdb300.isChecked == true) {
//                    tvWaterRecord.text = "300 ml"
//                    var waterLevelNow = waterNow + 300
//                    context?.let { it1 -> AppConfig.setWaterlevel(waterLevelNow, it1) }
//                    context?.let { it1 -> AppConfig.setWaterup(300, it1) }
//                    context?.let { it1 -> AppConfig.setWaterup2(300, it1) }
//                } else if (rdb400.isChecked == true) {
//                    tvWaterRecord.text = "400 ml"
//                    var waterLevelNow = waterNow + 400
//                    context?.let { it1 -> AppConfig.setWaterlevel(waterLevelNow, it1) }
//                    context?.let { it1 -> AppConfig.setWaterup(400, it1) }
//                    context?.let { it1 -> AppConfig.setWaterup2(400, it1) }
//                }
//                var waterUp = context?.let { it1 -> AppConfig.getWaterup(it1) }
//                val intent1 = Intent()
//                intent1.action = "textChange2"
//                intent1.putExtra("text2", "$waterUp")
//                context!!.sendBroadcast(intent1)
//                mAlertDialog.dismiss()
//
//            }
//
//
//        }

    }

    internal var receiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "textChange2") {

                val text = intent.getStringExtra("text2")
//                tvWaterRecord.text = "+$text ml"

            }
        }
    }

    private fun loadData() {



//        var record = context?.let { AppConfig.getRecord(it) }
//        if (record == true) {
//            rlRecord.visibility = View.VISIBLE
//        } else {
//            rlRecord.visibility = View.INVISIBLE
//        }
//        tvTimeRecord.text = context?.let { AppConfig.getTimeDrink(it) }
        var waterUp2 = context?.let { AppConfig.getWaterup2(it) }
        var waterUp = context?.let { AppConfig.getWaterup(it) }
//        tvWaterRecord.text = "+$waterUp2 ml"

        tvTimeWaterNext.text = context?.let { AppConfig.getTimeNextDrink(it) }

//        if(waterLevel>waterGold){
//            tvWaterNext.text = "0 ml"
//        }else{
            tvWaterNext.text = "$waterUp ml"
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(receiver2)
        context!!.unregisterReceiver(receiver)
        context!!.unregisterReceiver(receiver3)
    }

    internal var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "textChange") {
                Log.i("hihi", "hihi")
                val text = intent.getStringExtra("water").toInt()
//                tvWaterRecord.text = "+$text ml"
                var x = text.toInt()
                AppConfig.setWaterup2(x, context)
                var timeLast =  AppConfig.getTimeDrink(context!!)



            }
        }
    }
    internal var receiver3: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if(intent.action =="adapter"){
                    createRecycleView()
                    Log.i("recycleview","recycleview")
                }


            }
        }
    }


