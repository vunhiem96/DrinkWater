package com.vunhiem.drinkwater.screen.dailywatermain

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.vunhiem.drinkwater.Model.Weight
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper
import com.vunhiem.drinkwater.screen.settingweight.SettingWeight
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_set_weight.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class SetWeightActivity : AppCompatActivity() {

    private val FORMATTER = SimpleDateFormat.getDateInstance()
    var weightkg: Int? = 0
    var kg: Int = 80
    var kgDouble: Int = 0
    var weight: String = "80"
    lateinit var db: DBHelper
    lateinit var widget: CalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_weight)
        val c = Calendar.getInstance()
        val sdf2 = SimpleDateFormat("dd")
        weightkg = sdf2.format(c.getTime()).toInt()
        widget = findViewById(R.id.calendarView)
        loadData()
        onClick()

    }

    private fun onClick() {
        back.setOnClickListener {
            finish()
        }
        widget.setOnDateChangeListener { view, year, month, dayOfMonth ->
            weightkg = dayOfMonth
        }

        btn_save.setOnClickListener {
            val c = Calendar.getInstance()
            val sdf2 = SimpleDateFormat("dd")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
           if (weightkg == currentDate2) {

               if (kgDouble == 0) {
                   weight = "$kg"
               } else {
                   weight = "$kg.$kgDouble"
               }
               AppConfig.setWeight(weight, this)
            var check = AppConfig.getCheckDailyGold(this)
               val y = weight.toFloat()
               var ml = y / 0.03
               val twoDForm = DecimalFormat("#")
               var xx = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
               if(check==true) {
                   AppConfig.setGoldDrink("$xx", this)
               }
               var month = weightkg!!.toInt()
               db.updateWeightMonth(Weight(month, y))

           }else{
               if (kgDouble == 0) {
                   weight = "$kg"
               } else {
                   weight = "$kg.$kgDouble"
               }
               val y = weight.toFloat()
               var month = weightkg!!.toInt()
               db.updateWeightMonth(Weight(month, y))
           }
            finish()
        }
        img_setheight.setOnClickListener {
            val intent = Intent(this, SettingWeight::class.java)
            startActivity(intent)
        }
    }

    private fun loadData() {
        db = DBHelper(this)
        var i = intent.getStringExtra("weight")
        val y = i.toFloat()
        kg = y.toInt()
        kgDouble = ((y - kg) / 0.1).toInt()

        number_picker2_weight.value = kgDouble
        number_picker_weighr.value = kg

        number_picker_weighr.setOnValueChangedListener(object :
            NumberPicker.OnValueChangeListener,
            com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onValueChange(
                picker: com.shawnlin.numberpicker.NumberPicker?,
                oldVal: Int,
                newVal: Int
            ) {
                if (oldVal != newVal) {
                    kg = newVal

                } else {
                    kg = oldVal

                }
            }


        })
        number_picker2_weight.setOnValueChangedListener(object :
            NumberPicker.OnValueChangeListener,
            com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onValueChange(
                picker: com.shawnlin.numberpicker.NumberPicker?,
                oldVal: Int,
                newVal: Int
            ) {
                if (oldVal != newVal) {
                    kgDouble = newVal
                } else {
                    kgDouble = oldVal
                }
            }


        })

    }
}




