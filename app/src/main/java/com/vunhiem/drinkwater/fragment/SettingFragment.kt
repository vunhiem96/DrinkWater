package com.vunhiem.drinkwater.fragment


import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.tv_title_dialog
import kotlinx.android.synthetic.main.dialog_setdailygold.*
import kotlinx.android.synthetic.main.dialog_time_select.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.select_gender.rl_female
import kotlinx.android.synthetic.main.select_gender.rl_male
import kotlinx.android.synthetic.main.select_reminder.*
import java.text.DecimalFormat


class SettingFragment : Fragment() {
    var hhwake: Int = 0
    var mmwake: Int = 0
    var hhbed: Int = 0
    var mmbed: Int = 0
    lateinit var tgbtnRemiberOff:ToggleButton
    lateinit var tgbtnReminderVibrate:ToggleButton
    lateinit var tgbtnReminderRing:ToggleButton

    lateinit var tvGenderTextView: TextView
    lateinit var tvReminder: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(com.vunhiem.drinkwater.R.layout.fragment_setting, container, false)
        tvGenderTextView = view.findViewById(R.id.tv_gender_setting)
        tvReminder = view.findViewById(R.id.tv_reminder)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadData()
        onClick()
    }

    private fun onClick() {

        ln_reset.setOnClickListener {
            AppConfig.setWaterlevel(0,context!!)
            AppConfig.setCount(0,context!!)
            Toast.makeText(context, "Reset water level", Toast.LENGTH_LONG)
                .show()
        }

        ln_daily_gold.setOnClickListener {
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_setdailygold, null)

            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val  mAlertDialog = mBuilder!!.show()
            val y = context?.let { it1 -> AppConfig.getGoldDrink(it1) }!!.replace("[^\\d.]".toRegex(), "").toInt()
            var edtMl:EditText = mDialogView.findViewById(R.id.edt_daily_gold)
            edtMl = mAlertDialog.edt_daily_gold
            edtMl!!.hint = y.toString()

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btnAccept:Button = mDialogView.findViewById(R.id.btn_accept_daily)
            btnAccept.setOnClickListener {

                var textMl:String = edtMl!!.text.toString()
                if(textMl!="") {
                    AppConfig.setDailyGold(false, context!!)
                    tv_daily_gold_setting.text = "$textMl ml"
                }
                else{
                    tv_daily_gold_setting.text = "$y ml"
                }

                var x = tv_daily_gold_setting.text.toString()
                val y = x!!.replace("[^\\d.]".toRegex(), "").toInt()
                context?.let { it1 -> AppConfig.setGoldDrink("$y", it1) }
                mAlertDialog.dismiss()
            }
        }

        tgbtn_reminder.setOnClickListener {
            if(tgbtn_reminder.isChecked==true){
                context?.let { it1 -> AppConfig.setReminder(true, it1) }
            } else{
                context?.let { it1 -> AppConfig.setReminder(false, it1) }
            }
        }
        ln_interval_time_setting.setOnClickListener {
            val mDialogView = LayoutInflater.from(context)
                .inflate(com.vunhiem.drinkwater.R.layout.dialog, null)
            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            var btnAccept: Button = mDialogView.findViewById(com.vunhiem.drinkwater.R.id.btn_accept)
            var time = context?.let { it1 -> AppConfig.getIntervalTime(it1) }!!.replace("[^\\d.]".toRegex(), "")
            mAlertDialog.edt_kg.hint="$time"
            mAlertDialog.tv_title_dialog.text = "Interval time"
            mAlertDialog.tv_cm.text="minutes"
            btnAccept.setOnClickListener {
                var edtTimeBed: EditText
                edtTimeBed = mAlertDialog.edt_kg
                var textTimeinterval: String = edtTimeBed!!.text.toString()
                if (textTimeinterval != "") {
                    tv_interval_setting.text = "$textTimeinterval min "

                } else {
                    tv_interval_setting.text = "$time min"
                }
                context?.let { it1 ->
                    AppConfig.setIntervalTime(
                        tv_interval_setting.text.toString(),
                        it1
                    )
                }
                mAlertDialog.dismiss()
            }
        }
        ln_genger_setting.setOnClickListener {
            var btnAccept:Button
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.select_gender, null)

            //AlertDialogBuilder
            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
//            .setTitle("Login Form")
            //show dialog
            val  mAlertDialog = mBuilder!!.show()
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            tvGenderTextView.text="Male"
            btnAccept = mDialogView.findViewById(R.id.btn_accept)
            btnAccept.setOnClickListener {
                mAlertDialog.dismiss()
            }
            var tgbtnMale:ToggleButton = mDialogView.findViewById(R.id.tgbtn_male)
            var tgbtnFemale:ToggleButton = mDialogView.findViewById(R.id.tgbtn_female)
            mAlertDialog.rl_male.setOnClickListener {
                tgbtnMale!!.isChecked=true
                tgbtnFemale!!.isChecked=false
                tvGenderTextView.text="Male"
                context?.let { it1 -> AppConfig.setGender("Male", it1) }

            }
            mAlertDialog.rl_female.setOnClickListener {
                tgbtnMale!!.isChecked=false
                tgbtnFemale!!.isChecked=true
                tvGenderTextView.text="Female"
                context?.let { it1 -> AppConfig.setGender("Female", it1) }
            }
        }
        ln_weight_setting.setOnClickListener {
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)

            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val  mAlertDialog = mBuilder!!.show()
            val y = context?.let { it1 -> AppConfig.getWeight(it1) }!!.replace("[^\\d.]".toRegex(), "").toFloat()
            var edtKg:EditText = mDialogView.findViewById(R.id.edt_kg)
            edtKg = mAlertDialog.edt_kg
            edtKg!!.hint = y.toString()

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btnAccept:Button = mDialogView.findViewById(R.id.btn_accept)
            btnAccept.setOnClickListener {

                var textKg:String = edtKg!!.text.toString()
                if(textKg!="") {
                    tv_weight_setting.text = "$textKg kg"
                }
                else{
                    tv_weight_setting.text = "$y kg"
                }
//                context?.let { it1 -> AppConfig.setWeight(tv_weight_setting.text.toString(), it1) }
                var x = tv_weight_setting.text.toString()
                val y = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                context?.let { it1 -> AppConfig.setWeight("$y", it1) }
                var ml = y/0.03
                val twoDForm = DecimalFormat("#")
                var xx = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
                AppConfig.setGoldDrink("$xx", context!!)
                mAlertDialog.dismiss()
            }
        }
        ln_wakeup_time.setOnClickListener {
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_time_select, null)


            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()

             var currentString = context?.let { it1 -> AppConfig.getWakeUp(it1) }
             var separated = currentString!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
             var x = separated[0].toInt()// this will contain "Fruit"
             var y = separated[1].toInt()
                mAlertDialog.number_picker.value = x
                mAlertDialog.number_picker2.value = y
                hhwake=x
                mmwake=y
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
                if(hhwake==0 && mmwake==0)
                {
                    tv_wakeup_time_setting.text = "$x:$y"
                }
                if (hhwake < 10 && mmwake < 10) {
                    tv_wakeup_time_setting.text = "0$hhwake:0$mmwake"
                } else if (hhwake < 10 && mmwake > 9) {
                    tv_wakeup_time_setting.text = "0$hhwake:$mmwake"
                } else if (hhwake > 10 && mmwake < 10) {
                    tv_wakeup_time_setting.text = "$hhwake:0$mmwake"
                } else {
                    tv_wakeup_time_setting.text = "$hhwake:$mmwake"
                }
                context?.let { it1 -> AppConfig.setWakeUp(tv_wakeup_time_setting.text.toString(), it1) }
                mAlertDialog.dismiss()
            }
        }
        ln_bed_time.setOnClickListener {
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_time_select, null)
            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()
            var currentString = context?.let { it1 -> AppConfig.getBedTime(it1) }
            var separated = currentString!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var x = separated[0].toInt()// this will contain "Fruit"
            var y = separated[1].toInt()
            mAlertDialog.tv_title_dialog.text = "Bed time"
            mAlertDialog.number_picker.value = x
            mAlertDialog.number_picker2.value = y
            hhbed=x
            mmbed=y
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
                    tv_bedtime_setting.text = "0$hhbed:0$mmbed"
                } else if (hhbed < 10 && mmbed > 9) {
                    tv_bedtime_setting.text = "0$hhbed:$mmbed"
                } else if (hhbed > 10 && mmbed < 10) {
                    tv_bedtime_setting.text = "$hhbed:0$mmbed"
                } else {
                    tv_bedtime_setting.text = "$hhbed:$mmbed"
                }
                context?.let { it1 -> AppConfig.setBedTime(tv_bedtime_setting.text.toString(), it1) }
                mAlertDialog.dismiss()
            }
        }

        ln_reminder.setOnClickListener {

            var btnAccept:Button
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.select_reminder, null)

            //AlertDialogBuilder
            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }

            val  mAlertDialog = mBuilder!!.show()
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            tgbtnRemiberOff = mDialogView.findViewById(R.id.tgbtn_reminderoff)
            tgbtnReminderVibrate= mDialogView.findViewById(R.id.tgbtn_reminder_vibrate)
            tgbtnReminderRing = mDialogView.findViewById(R.id.tgbtn_reminderring)
            var z = context?.let { AppConfig.getRemiderMode(it) }
            if(z==0){
                tgbtnRemiberOff.isChecked=true
                tgbtnReminderVibrate.isChecked=false
                tgbtnReminderRing.isChecked=false
            }
            if(z==1){
                tgbtnRemiberOff.isChecked=false
                tgbtnReminderVibrate.isChecked=true
                tgbtnReminderRing.isChecked=false
            }
            if(z==2){
                tgbtnRemiberOff.isChecked=false
                tgbtnReminderVibrate.isChecked=false
                tgbtnReminderRing.isChecked=true
            }
            btnAccept = mDialogView.findViewById(R.id.btn_accept)
            btnAccept.setOnClickListener {
                mAlertDialog.dismiss()
            }

            mAlertDialog.rl_reminderoff.setOnClickListener {
                tgbtnRemiberOff.isChecked=true
                tgbtnReminderVibrate.isChecked=false
                tgbtnReminderRing.isChecked=false
                tvReminder.text="Reminder off"
                context?.let { it1 -> AppConfig.setRemiderMode(0, it1) }


            }
            mAlertDialog.rl_reminder_vibrate.setOnClickListener {
                tgbtnRemiberOff.isChecked=false
                tgbtnReminderVibrate.isChecked=true
                tgbtnReminderRing.isChecked=false
                tvReminder.text="Vibrate only"
                context?.let { it1 -> AppConfig.setRemiderMode(1, it1) }
            }
            mAlertDialog.rl_reminder_ring.setOnClickListener {
                tgbtnRemiberOff.isChecked=false
                tgbtnReminderVibrate.isChecked=false
                tgbtnReminderRing.isChecked=true
                tvReminder.text="Ringtine with vibrate"
                context?.let { it1 -> AppConfig.setRemiderMode(2, it1) }
            }
        }
        ln_feed_back.setOnClickListener {
            val uri = Uri.parse("market://details?id=" + context!!.packageName)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context!!.packageName))
                )
            }
        }
        ln_share.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                Uri.parse("https://play.google.com/store/apps/details?id=" + context!!.packageName))
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }


    private fun loadData() {
        tgbtn_reminder.isChecked = context?.let { AppConfig.getReminder(it) }!!

        tv_gender_setting.text = context?.let { AppConfig.getGender(it) }
        var y = context?.let { AppConfig.getGoldDrink(it) }
        tv_daily_gold_setting.text = "$y ml"
        var x =context?.let { AppConfig.getWeight(it) }
        tv_weight_setting.text = "$x kg"
        tv_wakeup_time_setting.text = context?.let { AppConfig.getWakeUp(it) }
        tv_interval_setting.text = context?.let { AppConfig.getIntervalTime(it) }
        tv_bedtime_setting.text = context?.let { AppConfig.getBedTime(it) }
        var z = context?.let { AppConfig.getRemiderMode(it) }
        if(z==0){
            tvReminder.text="Reminder off"
        }
        if(z==1){
            tvReminder.text="Vibrate only"
        }
        if(z==2){
            tvReminder.text="Ringtine with vibrate"
        }
    }

}
