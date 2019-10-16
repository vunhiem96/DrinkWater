package com.vunhiem.drinkwater.screen.dailywater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vunhiem.drinkwater.screen.dailywatermain.DrinkwaterMainActivity
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_daily_water.*
import java.text.DecimalFormat




class DailyWaterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_water)
        loadData()

        btn_dailywater.setOnClickListener {
            AppConfig.setHistoryLogin(true, this)
            AppConfig.setGoldDrink(tv_water_need_drink.text.toString(),this@DailyWaterActivity)
            val intent = Intent(this, DrinkwaterMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadData() {
        var x = AppConfig.getWeight(this@DailyWaterActivity)
        val y = x!!.replace("[^\\d.]".toRegex(), "").toInt()
        var ml = y/0.03
        val twoDForm = DecimalFormat("#")
        var xx = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
        tv_water_need_drink.text="$xx"



    }
}
