package com.vunhiem.drinkwater.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.RecyclerView
import com.vunhiem.drinkwater.Model.WaterHistory
import com.vunhiem.drinkwater.Model.WaterMonth
import com.vunhiem.drinkwater.Model.WaterWeek
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.item_history.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter(
    var context: Context,
    var noti: ArrayList<WaterHistory>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var db: DBHelper? = null
    var check: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return noti.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        db = DBHelper(context)
        var data: WaterHistory = noti[position]
        var time = data.time
        var ml = data.waterUp
        holder.itemView.tv_time_records_item.text = "$time"
        holder.itemView.tv_water_record_item.text = "+$ml ml"
        holder.itemView.more.setOnClickListener {
            val wrapper = ContextThemeWrapper(context, R.style.CustomPopupTheme)
            val menuBuilder = MenuBuilder(wrapper)
            val inflater = MenuInflater(wrapper)

            inflater.inflate(R.menu.edit_today_item, menuBuilder)
            val optionsMenu =
                MenuPopupHelper(wrapper, menuBuilder, holder.itemView.more)
            optionsMenu.setForceShowIcon(true)
            menuBuilder.setCallback(object : MenuBuilder.Callback {
                override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.nav_edit
                            // Handle option1 Click
                        -> {

                            var cancle: TextView
                            var ok: TextView
                            val rdb100: RadioButton
                            val rdb200: RadioButton
                            val rdb300: RadioButton
                            val rdb400: RadioButton

                            check = false
                            val mDialogView =
                                LayoutInflater.from(context).inflate(R.layout.dialog_history, null)

                            //AlertDialogBuilder
                            val mBuilder = context?.let { it1 ->
                                AlertDialog.Builder(it1)
                                    .setView(mDialogView)
                            }

                            val mAlertDialog = mBuilder!!.show()
                            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            rdb100 = mDialogView.findViewById(R.id.rdb_100)
                            rdb200 = mDialogView.findViewById(R.id.rdb_200)
                            rdb300 = mDialogView.findViewById(R.id.rdb_300)
                            rdb400 = mDialogView.findViewById(R.id.rdb_400)

                            var text=  holder.itemView.tv_water_record_item.text
                            var waterOldUp = text.replace("[^\\d.]".toRegex(), "").toInt()

                            if (waterOldUp == 100) {
                                rdb100.isChecked = true
                            } else if (waterOldUp == 200) {
                                rdb200.isChecked = true
                            } else if (waterOldUp == 300) {
                                rdb300.isChecked = true
                            } else if (waterOldUp == 400) {
                                rdb400.isChecked = true
                            }
                            cancle = mDialogView.findViewById(R.id.tv_cancle)
                            ok = mDialogView.findViewById(R.id.tv_ok)
                            cancle.setOnClickListener {
                                mAlertDialog.dismiss()
                            }
                            ok.setOnClickListener {
                                var waterDrank =
                                    context?.let { it1 -> AppConfig.getWaterLevel(it1) }
                                var waterNow = waterDrank!! - waterOldUp!!
                                if (rdb100.isChecked == true) {

                                    holder.itemView.tv_water_record_item.text = "+100 ml"
                                    var waterLevelNow = waterNow + 100
                                    context?.let { it1 ->
                                        AppConfig.setWaterlevel(
                                            waterLevelNow,
                                            it1
                                        )
                                    }
//                                    if (position == noti.size-1){
//                                        context?.let { it1 -> AppConfig.setWaterup(100, it1) }
//                                    }
                                    context?.let { it1 -> AppConfig.setWaterup2(100, it1) }
                                    var id = data.id
                                    var time = data.time.toString()
                                    var waterUp = 100
                                    var history = WaterHistory(id, time, waterUp)
                                    db!!.updateHistory(history)

                                } else if (rdb200.isChecked == true) {

                                    holder.itemView.tv_water_record_item.text = "+200 ml"
                                    var waterLevelNow = waterNow + 200
                                    context?.let { it1 ->
                                        AppConfig.setWaterlevel(
                                            waterLevelNow,
                                            it1
                                        )
                                    }
//                                    if (position == noti.size-1){
//                                        context?.let { it1 -> AppConfig.setWaterup(200, it1) }
//                                    }
                                    context?.let { it1 -> AppConfig.setWaterup2(200, it1) }
                                    var id = data.id
                                    var time = data.time.toString()
                                    var waterUp = 200
                                    var history = WaterHistory(id, time, waterUp)
                                    db!!.updateHistory(history)

                                } else if (rdb300.isChecked == true) {

                                    holder.itemView.tv_water_record_item.text = "+300 ml"
                                    var waterLevelNow = waterNow + 300
                                    context?.let { it1 ->
                                        AppConfig.setWaterlevel(
                                            waterLevelNow,
                                            it1
                                        )
                                    }
//                                    if (position == noti.size-1){
//                                        context?.let { it1 -> AppConfig.setWaterup(300, it1) }
//                                    }
                                    context?.let { it1 -> AppConfig.setWaterup2(300, it1) }
                                    var id = data.id
                                    var time = data.time.toString()
                                    var waterUp = 300
                                    var history = WaterHistory(id, time, waterUp)
                                    db!!.updateHistory(history)

                                } else if (rdb400.isChecked == true) {

                                    holder.itemView.tv_water_record_item.text = "+400 ml"
                                    var waterLevelNow = waterNow + 400
                                    context?.let { it1 ->
                                        AppConfig.setWaterlevel(
                                            waterLevelNow,
                                            it1
                                        )
                                    }
//                                    if (position == noti.size-1){
//                                        context?.let { it1 -> AppConfig.setWaterup(400, it1) }
//                                    }

                                    context?.let { it1 -> AppConfig.setWaterup2(400, it1) }
                                    var id = data.id
                                    var time = data.time.toString()
                                    var waterUp = 400
                                    var history = WaterHistory(id, time, waterUp)
                                    db!!.updateHistory(history)

                                }
//                                if (position == noti.size-1) {
////                                    Log.i("position","$position")
////                                    var waterUp = context?.let { it1 -> AppConfig.getWaterup(it1) }
////                                    val intent1 = Intent()
////                                    intent1.action = "textChange2"
////                                    intent1.putExtra("text2", "$waterUp")
////                                    context!!.sendBroadcast(intent1)
//
//                                }




                                val intentChange = Intent()
                                intentChange.action = "adapter"
                                var waterUp = AppConfig.getWaterup(context)
                                Log.i("waterup","$waterUp")
                                if (position == noti.size-1){
                                    intentChange.putExtra("adapterWaterlevel", waterUp)
                                }
                                context.sendBroadcast(intentChange)
                                updateData()
                                mAlertDialog.dismiss()
                            }




                            return true
                        }
                        R.id.nav_delete
                        -> {
                            if (position == noti.size-1 && noti.size>1) {
                                var positon2 = position -1
                                var x = noti[positon2]
//                                var waterup = holder.itemView.tv_water_record_item.text.replace("[^\\d.]".toRegex(), "").toInt()
                                var waterUp = x.waterUp!!.toInt()
                               AppConfig.setWaterup(waterUp, context!!)
                            }
                            var text=  holder.itemView.tv_water_record_item.text
                            var waterOldUp = text.replace("[^\\d.]".toRegex(), "").toInt()
                            var waterLevel = AppConfig.getWaterLevel(context!!)!!.toInt()
                            if(waterLevel >= 0){
                                var waternow = waterLevel!! - waterOldUp
                                AppConfig.setWaterlevel(waternow,context!!)
                            }
                           var count = AppConfig.getCount(context)!!.toInt()
                            if (count>0){
                                var countNow = count - 1
                                AppConfig.setCount(countNow, context)
                               updateData()

                            }
                            var id = data.id
                            db!!.deleteOneHistory(id)
                            noti.removeAt(position)
                            notifyDataSetChanged()

                            return true
                        }
                        else -> return false
                    }
                }

                override fun onMenuModeChange(menu: MenuBuilder) {}
            })

            optionsMenu.show()


        }
    }
    fun updateData() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        if (day == Calendar.MONDAY) {
            Log.i("test26", "monday")
            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
            var waterDrink =
                AppConfig.getWaterLevel(context)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(context)
            val water =
                WaterWeek(2, waterDrink2, waterCompletion2, count!!)
            db!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            db!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.TUESDAY) {
            Log.i("test26", "tue")
            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
            var waterDrink =
                AppConfig.getWaterLevel(context)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(context)
            val water =
                WaterWeek(3, waterDrink2, waterCompletion2, count!!)
            db!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            db!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.WEDNESDAY) {
            Log.i("test26", "wed")
            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
            var waterDrink =
                AppConfig.getWaterLevel(context)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(context)
            val water =
                WaterWeek(4, waterDrink2, waterCompletion2, count!!)
            db!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            db!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.THURSDAY) {
            Log.i("test26", "thur")
            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
            var waterDrink =
                AppConfig.getWaterLevel(context)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(context)
            val water =
                WaterWeek(5, waterDrink2, waterCompletion2, count!!)
            db!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            db!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.FRIDAY) {
            Log.i("test26", "fri")
            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
            var waterDrink =
                AppConfig.getWaterLevel(context)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(context)
            val water =
                WaterWeek(6, waterDrink2, waterCompletion2, count!!)
            db!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            db!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.SATURDAY) {
            Log.i("test26", "sar")
            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
            var waterDrink =
                AppConfig.getWaterLevel(context)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(context)
            val water =
                WaterWeek(7, waterDrink2, waterCompletion2, count!!)
            db!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            db!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.SUNDAY) {
            Log.i("test26", "sun")
            var waterDrink2 = AppConfig.getWaterLevel(context)!!.toInt()
            var waterDrink =
                AppConfig.getWaterLevel(context)!!.toFloat()
            var waterGold = AppConfig.getGoldDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            var waterCompletion = (waterDrink / waterGold2) * 100
            var waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            var count = AppConfig.getCount(context)
            val water =
                WaterWeek(8, waterDrink2, waterCompletion2, count!!)
            db!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            db!!.updateWaterMonth(waterMonth)
        }
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}