package com.vunhiem.drinkwater.screen.settingweight

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_setting_weight.*
import kotlinx.android.synthetic.main.dialog2.*

class SettingWeight : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_weight)
        loadData()
        onClick()
    }

    private fun loadData() {
        var height = AppConfig.getHeight(this)
        tv_height.text = "$height cm"
        var targetWeight = AppConfig.getTargetWeight(this)
        tv_target.text= "$targetWeight kg"
    }

    private fun onClick() {
        img_back.setOnClickListener {
            finish()
        }
        ln_height.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog2, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            val y = tv_height.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
            var edtKg = mAlertDialog.edt_daily_gold

            edtKg!!.hint = y.toString()

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.tv_title_dialog2.text = "My height"
            mAlertDialog.tv_ml2.text = "cm"



            mAlertDialog.btn_accept_daily.setOnClickListener {
                edtKg = mAlertDialog.edt_daily_gold
                var textCm: String = edtKg!!.text.toString()
                if (textCm != "") {
                    tv_height.text = "$textCm cm"
                } else {
                    tv_height.text = "$y cm"
                }
                var x = tv_height.text.toString()
                val z = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                AppConfig.setHeight("$z",this)
                mAlertDialog.dismiss()
            }
        }
        ln_target.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog2, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            val y = tv_target.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
           var edtKg = mAlertDialog.edt_daily_gold
            edtKg!!.hint = y.toString()

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.btn_accept_daily.setOnClickListener {

                var textKg: String = edtKg!!.text.toString()
                if (textKg != "") {
                    tv_target.text = "$textKg kg"
                } else {
                    tv_target.text = "$y kg"
                }
                mAlertDialog.dismiss()
                var x = tv_target.text.toString()
                val z = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                AppConfig.setTargetWeight("$z",this)
            }
        }
    }
}
