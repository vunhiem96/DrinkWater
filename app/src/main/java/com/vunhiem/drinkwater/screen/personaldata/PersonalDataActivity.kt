package com.vunhiem.drinkwater.screen.personaldata

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.fragment.waterDrank
import com.vunhiem.drinkwater.screen.dailywater.DailyWaterActivity
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_personal_data.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog_time_select.*
import kotlinx.android.synthetic.main.select_gender.*
import kotlinx.android.synthetic.main.select_gender.btn_accept


class PersonalDataActivity : AppCompatActivity(), waterDrank {
    override fun textChange(water: String) {
        tv_wavep_main.text = water
    }

    lateinit var btnAccept: Button
    var hhwake: Int = 8
    var mmwake: Int = 0
    var hhbed: Int = 23
    var mmbed: Int = 0
    private var tgbtnMale: ToggleButton? = null
    private var tgbtnFemale: ToggleButton? = null
    private var edtKg: EditText? = null
    private var edtTimeBed: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)
        onClick()

    }


    private fun onClick() {
        rl_gender.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.select_gender, null)


            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            tgbtnMale = mAlertDialog.tgbtn_male
            tgbtnFemale = mAlertDialog.tgbtn_female
            var gender = tv_gender.text
            if (gender == "Male") {
                tgbtnMale!!.isChecked = true
                tgbtnFemale!!.isChecked = false

            } else {
                tgbtnMale!!.isChecked = false
                tgbtnFemale!!.isChecked = true

            }


            mAlertDialog.rl_male.setOnClickListener {
                tgbtnMale!!.isChecked = true
                tgbtnFemale!!.isChecked = false


            }
            mAlertDialog.rl_female.setOnClickListener {
                tgbtnMale!!.isChecked = false
                tgbtnFemale!!.isChecked = true

            }
            mAlertDialog.btn_accept.setOnClickListener {
                var x = tgbtnMale!!.isChecked
                if (x == true) {
                    tv_gender.text = "Male"
                } else {
                    tv_gender.text = "Female"
                }
                mAlertDialog.dismiss()
            }
        }

        rl_weight.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            val y = tv_kg.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
            edtKg = mAlertDialog.edt_kg
            edtKg!!.setText(y.toString())

            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtKg!!.setSelection(edtKg!!.getText().length)
                    }, 100)
                }
                return@OnFocusChangeListener
            }
            edtKg!!.setOnFocusChangeListener(onFocusChangeListener)

            edtKg!!.setOnClickListener {
                edtKg!!.setSelection(edtKg!!.getText().length)
            }
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.btn_accept.setOnClickListener {

                var textKg: String = edtKg!!.text.toString()
                var textKg2: String = edtKg!!.text.toString().trim()
                Log.i("hjha", "$textKg")
                if (textKg != "") {
                    Log.i("hjha", "$textKg")
                    if (textKg == ".") {
                        mAlertDialog.tv_title_dialog3.text = "Invalid weight"
                        val handler = Handler()
                        handler.postDelayed({
                            mAlertDialog.tv_title_dialog3.text = "My weight"
                            edtKg!!.text=null
                        }, 3000)
                    } else {
                        var x = textKg.toFloat()
                        if (10 < x && x < 200) {
                            tv_kg.text = "$textKg kg"
                            mAlertDialog.dismiss()
                        } else {
                            mAlertDialog.tv_title_dialog3.text = "Invalid weight"
                            val handler = Handler()
                            handler.postDelayed({
                                mAlertDialog.tv_title_dialog3.text = "My weight"
                                edtKg!!.text=null
                            }, 3000)
                        }
                    }
                } else {
                    tv_kg.text = "$y kg"
                    mAlertDialog.dismiss()
                }

            }
        }

        rl_height.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.setCanceledOnTouchOutside(false)
            val y = tv_cm_main.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
            edtKg = mAlertDialog.edt_kg
            edtKg!!.setText(y.toString())

            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtKg!!.setSelection(edtKg!!.getText().length)
                    }, 100)
                }
                return@OnFocusChangeListener
            }
            edtKg!!.setOnFocusChangeListener(onFocusChangeListener)

            edtKg!!.setOnClickListener {
                edtKg!!.setSelection(edtKg!!.getText().length)
            }

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.tv_title_dialog3.text = "My height"
            mAlertDialog.tv_cm.text = "cm"



            mAlertDialog.btn_accept.setOnClickListener {
                edtKg = mAlertDialog.edt_kg
                var textCm: String = edtKg!!.text.toString()
                if (textCm != "") {
                    if (textCm == ".") {
                        mAlertDialog.tv_title_dialog3.text = "Invalid height"
                        val handler = Handler()
                        handler.postDelayed({
                            mAlertDialog.tv_title_dialog3.text = "My height"
                            edtKg!!.text=null
                        }, 3000)
                    } else {
                        var y = textCm.toFloat()
                        if (y > 30 && y < 250) {
                            tv_cm_main.text = "$textCm cm"
                            mAlertDialog.dismiss()
                        } else {
                            mAlertDialog.tv_title_dialog3.text = "Invalid height"
                            val handler = Handler()
                            handler.postDelayed({
                                mAlertDialog.tv_title_dialog3.text = "My height"
                                edtKg!!.text=null
                            }, 3000)
                        }
                    }
                } else {
                    tv_cm_main.text = "$y cm"
                    mAlertDialog.dismiss()
                }

            }
        }

        rl_time_waveup.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_time_select, null)


            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.setCanceledOnTouchOutside(false)
            mAlertDialog.number_picker.value = hhwake
            mAlertDialog.number_picker2.value = mmwake
            mAlertDialog.number_picker2.setFormatter(R.string.number_picker_formatter)
            mAlertDialog.number_picker.setFormatter(R.string.number_picker_formatter)
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.number_picker.setOnValueChangedListener(object :
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
                        hhwake = newVal
                    } else {
                        hhwake = oldVal
                    }
                }


            })
            mAlertDialog.number_picker2.setOnValueChangedListener(object :
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
                        mmwake = newVal
                    } else {
                        mmwake = oldVal
                    }
                }

            })

            mAlertDialog.btn_accept_time.setOnClickListener {
                if (hhwake < 10 && mmwake < 10) {
                    tv_wavep_main.text = "0$hhwake:0$mmwake"
                } else if (hhwake < 10 && mmwake > 9) {
                    tv_wavep_main.text = "0$hhwake:$mmwake"
                } else if (hhwake > 10 && mmwake < 10) {
                    tv_wavep_main.text = "$hhwake:0$mmwake"
                } else {
                    tv_wavep_main.text = "$hhwake:$mmwake"
                }

                mAlertDialog.dismiss()
            }
        }

        rl_bed_time.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_time_select, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            mAlertDialog.setCanceledOnTouchOutside(false)

            mAlertDialog.tv_title_dialog.text = "Bed time"
            mAlertDialog.number_picker.value = hhbed
            mAlertDialog.number_picker2.value = mmbed
            mAlertDialog.number_picker.setFormatter(R.string.number_picker_formatter)
            mAlertDialog.number_picker2.setFormatter(R.string.number_picker_formatter)
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.number_picker.setOnValueChangedListener(object :
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
                        hhbed = newVal
                    } else {
                        hhbed = oldVal
                    }
                }


            })
            mAlertDialog.number_picker2.setOnValueChangedListener(object :
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
                        mmbed = newVal
                    } else {
                        mmbed = oldVal
                    }
                }

            })

            mAlertDialog.btn_accept_time.setOnClickListener {
                if (hhbed < 10 && mmbed < 10) {
                    tv_bedtime.text = "0$hhbed:0$mmbed"
                } else if (hhbed < 10 && mmbed > 9) {
                    tv_bedtime.text = "0$hhbed:$mmbed"
                } else if (hhbed > 10 && mmbed < 10) {
                    tv_bedtime.text = "$hhbed:0$mmbed"
                } else {
                    tv_bedtime.text = "$hhbed:$mmbed"
                }

                mAlertDialog.dismiss()
            }
        }

        rl_interval_time.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_interval_time, null)

            var tgbtn30: ToggleButton = mDialogView.findViewById(R.id.tgbtn_30)
            var tgbtn1: ToggleButton = mDialogView.findViewById(R.id.tgbtn_1)
            var tgbtn130: ToggleButton = mDialogView.findViewById(R.id.tgbtn_130)
            var tgbtn2: ToggleButton = mDialogView.findViewById(R.id.tgbtn_2)
            var tgbtn3: ToggleButton = mDialogView.findViewById(R.id.tgbtn_3)

            var rl_30: RelativeLayout = mDialogView.findViewById(R.id.rl_30)
            var rl_1: RelativeLayout = mDialogView.findViewById(R.id.rl_1)
            var rl_130: RelativeLayout = mDialogView.findViewById(R.id.rl_130)
            var rl_2: RelativeLayout = mDialogView.findViewById(R.id.rl_2)
            var rl_3: RelativeLayout = mDialogView.findViewById(R.id.rl_3)

            btnAccept = mDialogView.findViewById(R.id.btn_accept_interval_time)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            var time = AppConfig.getIntervalTime(this)
            if (time == "30 min") {
                tgbtn30.isChecked = true
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false

            } else if (time == "60 min") {
                tgbtn30.isChecked = false
                tgbtn1.isChecked = true
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false

            } else if (time == "90 min") {
                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = true
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false
            } else if (time == "120 min") {
                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = true
                tgbtn3.isChecked = false

            } else if (time == "180 min") {
                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = true
            }
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            var temp = 0
            rl_30.setOnClickListener {
                temp = 1
//                tv_time_interval.text = "30 min"
                tgbtn30.isChecked = true
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false
//                AppConfig.setIntervalTime("30 min", this)
            }
            rl_1.setOnClickListener {
                temp = 2

                tgbtn30.isChecked = false
                tgbtn1.isChecked = true
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false

            }
            rl_130.setOnClickListener {
                temp = 3

                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = true
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false

            }
            rl_2.setOnClickListener {
                temp = 4

                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = true
                tgbtn3.isChecked = false
            }
            rl_3.setOnClickListener {
                temp = 5

                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = true

            }
            btnAccept.setOnClickListener {
                if (temp == 1) {
                    tv_time_interval.text = "30 min"
                    AppConfig.setIntervalTime("30 min", this)
                } else if (temp == 2) {
                    tv_time_interval.text = "1 hour"
                    AppConfig.setIntervalTime("60 min", this)
                } else if (temp == 3) {
                    tv_time_interval.text = "90 min"
                    AppConfig.setIntervalTime("90 min", this)
                } else if (temp == 4) {
                    AppConfig.setIntervalTime("120 min", this)
                    tv_time_interval.text = "2 hour"
                } else if (temp == 5) {
                    AppConfig.setIntervalTime("180 min", this)
                    tv_time_interval.text = "3 hour"
                }
                mAlertDialog.dismiss()
            }


        }

        btn_next.setOnClickListener {
            AppConfig.setGender(tv_gender.text.toString(), this@PersonalDataActivity)
            val y = tv_kg.text.toString()!!.replace("[^\\d.]".toRegex(), "")
            AppConfig.setWeight(y, this@PersonalDataActivity)
            val height = tv_cm_main.text.toString()!!.replace("[^\\d.]".toRegex(), "")
            AppConfig.setHeight(height, this@PersonalDataActivity)
            AppConfig.setWakeUp(tv_wavep_main.text.toString(), this@PersonalDataActivity)
            AppConfig.setBedTime(tv_bedtime.text.toString(), this@PersonalDataActivity)

//            var time = tv_time_interval.text
//            if(time == "30 min"){
//            AppConfig.setIntervalTime(tv_time_interval.text.toString(), this@PersonalDataActivity)}
//            else(time=="")
//


            val intent = Intent(this, DailyWaterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}





