package com.vunhiem.drinkwater.screen.settingweight

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_personal_data.*
import kotlinx.android.synthetic.main.activity_setting_weight.*
import kotlinx.android.synthetic.main.dialog.*
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

            edtKg.setText(y.toString())

            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtKg.setSelection(edtKg.getText().length)
                    }, 100)
                }
                return@OnFocusChangeListener
            }
            edtKg.setOnFocusChangeListener(onFocusChangeListener)

            edtKg.setOnClickListener {
                edtKg.setSelection(edtKg.getText().length)
            }

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.tv_title_dialog2.text = "My height"
            mAlertDialog.tv_ml2.text = "cm"



            mAlertDialog.btn_accept_daily.setOnClickListener {
                edtKg = mAlertDialog.edt_daily_gold
                var textCm: String = edtKg!!.text.toString()
                if (textCm != "") {
                   if(textCm=="."){
                       mAlertDialog.tv_title_dialog2.text = "Invalid height"
                       val handler = Handler()
                       handler.postDelayed({
                           mAlertDialog.tv_title_dialog2.text = "My height"
                           edtKg!!.text=null
                       }, 4000)
                   }else {
                       var xz = textCm.toFloat()
                       if (30 < xz && xz < 250) {
                           tv_height.text = "$textCm cm"
                           var x = tv_height.text.toString()
                           val z = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                           AppConfig.setHeight("$z", this)
                           mAlertDialog.dismiss()
                       } else {
                           mAlertDialog.tv_title_dialog2.text = "Invalid height"
                           val handler = Handler()
                           handler.postDelayed({
                               mAlertDialog.tv_title_dialog2.text = "My height"
                               edtKg!!.text=null
                           }, 4000)
                       }

                   }

                } else {
                    tv_height.text = "$y cm"
                    var x = tv_height.text.toString()
                    val z = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                    AppConfig.setHeight("$z",this)
                    mAlertDialog.dismiss()
                }

            }
        }
        ln_target.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog2, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            val y = tv_target.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
           var edtKg = mAlertDialog.edt_daily_gold
            edtKg.setText(y.toString())

            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtKg.setSelection(edtKg.getText().length)
                    }, 100)
                }
                return@OnFocusChangeListener
            }
            edtKg.setOnFocusChangeListener(onFocusChangeListener)

            edtKg.setOnClickListener {
                edtKg.setSelection(edtKg.getText().length)
            }

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.tv_title_dialog2.text = "Target weight"
            mAlertDialog.btn_accept_daily.setOnClickListener {

                var textKg: String = edtKg!!.text.toString()
                if (textKg != "") {
                    if(textKg == "."){
                        mAlertDialog.tv_title_dialog2.text = "Invalid weight"
                        val handler = Handler()
                        handler.postDelayed({
                            mAlertDialog.tv_title_dialog2.text = "Target weight"
                            edtKg!!.text=null
                        }, 3000)
                    }else{
                        var xz = textKg.toFloat()
                        if(10<xz && xz<200) {
                            tv_target.text = "$textKg kg"
                            mAlertDialog.dismiss()
                            var x = tv_target.text.toString()
                            val z = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                            AppConfig.setTargetWeight("$z", this)
                        }else{
                            mAlertDialog.tv_title_dialog2.text = "Invalid weight"
                            val handler = Handler()
                            handler.postDelayed({
                                mAlertDialog.tv_title_dialog2.text = "Target weight"
                                edtKg!!.text=null
                            }, 3000)
                        }
                    }
                } else {
                    tv_target.text = "$y kg"
                    mAlertDialog.dismiss()
                    var x = tv_target.text.toString()
                    val z = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                    AppConfig.setTargetWeight("$z",this)
                }

            }
        }
    }
}
