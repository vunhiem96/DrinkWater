package com.vunhiem.drinkwater.screen.personaldata

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.fragment.waterDrank
import com.vunhiem.drinkwater.screen.dailywater.DailyWaterActivity
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_personal_data.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.tv_title_dialog
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
            tv_gender.text = "Male"
            mAlertDialog.btn_accept.setOnClickListener {
                mAlertDialog.dismiss()
            }
            tgbtnMale = mAlertDialog.tgbtn_male
            tgbtnFemale = mAlertDialog.tgbtn_female
            mAlertDialog.rl_male.setOnClickListener {
                tgbtnMale!!.isChecked = true
                tgbtnFemale!!.isChecked = false
                tv_gender.text = "Male"

            }
            mAlertDialog.rl_female.setOnClickListener {
                tgbtnMale!!.isChecked = false
                tgbtnFemale!!.isChecked = true
                tv_gender.text = "Female"
            }
        }

        rl_weight.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            val y = tv_kg.text!!.replace("[^\\d.]".toRegex(), "").toInt()
            edtKg = mAlertDialog.edt_kg
            edtKg!!.hint = y.toString()

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.btn_accept.setOnClickListener {

                var textKg: String = edtKg!!.text.toString()
                if (textKg != "") {
                    tv_kg.text = "$textKg kg"
                } else {
                    tv_kg.text = "$y kg"
                }
                mAlertDialog.dismiss()
            }
        }

        rl_height.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            val y = tv_cm_main.text!!.replace("[^\\d.]".toRegex(), "").toInt()
            edtKg = mAlertDialog.edt_kg
            edtKg!!.hint = y.toString()

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.tv_title_dialog.text = "My height"
            mAlertDialog.tv_cm.text = "cm"



            mAlertDialog.btn_accept.setOnClickListener {
                edtKg = mAlertDialog.edt_kg
                var textCm: String = edtKg!!.text.toString()
                if (textCm != "") {
                    tv_cm_main.text = "$textCm cm"
                } else {
                    tv_cm_main.text = "$y cm"
                }
                mAlertDialog.dismiss()
            }
        }

        rl_time_waveup.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_time_select, null)


            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
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
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            btnAccept = mDialogView.findViewById(R.id.btn_accept)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            val y = tv_time_interval.text!!.replace("[^\\d.]".toRegex(), "").toInt()
            edtKg = mAlertDialog.edt_kg
            edtKg!!.hint = y.toString()

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.tv_title_dialog.text = "Interval time"
            mAlertDialog.edt_kg.hint = y.toString()
            mAlertDialog.tv_cm.text = "minutes"
            btnAccept.setOnClickListener {
                edtTimeBed = mAlertDialog.edt_kg
                var textTimeinterval: String = edtTimeBed!!.text.toString()
                if (textTimeinterval != "") {
                    tv_time_interval.text = "$textTimeinterval min "
                } else {
                    tv_time_interval.text = "$y min"
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
            AppConfig.setIntervalTime(tv_time_interval.text.toString(), this@PersonalDataActivity)


            val intent = Intent(this, DailyWaterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}





