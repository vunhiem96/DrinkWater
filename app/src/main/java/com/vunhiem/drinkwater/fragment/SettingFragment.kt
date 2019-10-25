package com.vunhiem.drinkwater.fragment


import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.screen.develop.DevelopActivity
import com.vunhiem.drinkwater.screen.privacypolice.PrivacyPoliceActivity
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.activity_personal_data.*
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog_exit.*
import kotlinx.android.synthetic.main.dialog_setdailygold.*
import kotlinx.android.synthetic.main.dialog_time_select.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.select_gender.rl_female
import kotlinx.android.synthetic.main.select_gender.rl_male
import kotlinx.android.synthetic.main.select_reminder.*
import java.text.DecimalFormat
import android.widget.EditText
import com.vunhiem.drinkwater.Model.WaterMonth
import com.vunhiem.drinkwater.Model.Weight
import java.text.SimpleDateFormat
import java.util.*
import android.widget.CompoundButton





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

        ln_develop.setOnClickListener {
            val intent = Intent(context,DevelopActivity::class.java)
            context!!.startActivity(intent)
        }
        ln_privacy.setOnClickListener {
            val intent = Intent(context,PrivacyPoliceActivity::class.java)
            context!!.startActivity(intent)
        }

        ln_reset.setOnClickListener {
            val text:TextView
            val mDialogView = LayoutInflater.from(context!!).inflate(R.layout.dialog_exit, null)
            text = mDialogView.findViewById(R.id.tv_title_dialog_reset)
            val mBuilder = AlertDialog.Builder(context!!)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.tv_title_dialog_reset.text = "Do you reset water level?"
            mAlertDialog.btn_yes.setOnClickListener {
                AppConfig.setCheckFull(false, context!!)
                AppConfig.setWaterlevel(0,context!!)
                AppConfig.setCount(0,context!!)
                Toast.makeText(context, "Reset water level", Toast.LENGTH_LONG)
                    .show()
                mAlertDialog.dismiss()
            }
            mAlertDialog.btn_no.setOnClickListener {
                mAlertDialog.dismiss()
            }


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
            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtMl.setSelection(edtMl.getText().length)
                    }, 100)
                }
                return@OnFocusChangeListener
            }
            edtMl.setOnFocusChangeListener(onFocusChangeListener)

            edtMl.setOnClickListener {
                edtMl.setSelection(edtMl.getText().length)
            }

            edtMl = mAlertDialog.edt_daily_gold
            edtMl.setText(y.toString())





//            edtMl.setOnKeyListener(object : View.OnKeyListener {
//                override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
//                    edtMl.setSelection(edtMl.getText().length )
//                    return false
//                }
//            })




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

//        tgbtn_reminder.setOnClickListener {
//            if(tgbtn_reminder.isChecked==true){
//                context?.let { it1 -> AppConfig.setReminder(true, it1) }
//            } else{
//                context?.let { it1 -> AppConfig.setReminder(false, it1) }
//            }
//        }

        checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked==true){
                context?.let { it1 -> AppConfig.setReminder(true, it1) }
            } else{
                context?.let { it1 -> AppConfig.setReminder(false, it1) }
            }
        })


        ln_interval_time_setting.setOnClickListener {
//            val mDialogView = LayoutInflater.from(context)
//                .inflate(com.vunhiem.drinkwater.R.layout.dialog, null)
//            val mBuilder = context?.let { it1 ->
//                AlertDialog.Builder(it1)
//                    .setView(mDialogView)
//            }
//            val mAlertDialog = mBuilder!!.show()
//            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            var btnAccept: Button = mDialogView.findViewById(com.vunhiem.drinkwater.R.id.btn_accept)
//            var time = context?.let { it1 -> AppConfig.getIntervalTime(it1) }!!.replace("[^\\d.]".toRegex(), "")
//            mAlertDialog.edt_kg.hint="$time"
//            mAlertDialog.tv_title_dialog3.text = "Interval time"
//            mAlertDialog.tv_cm.text="minutes"
//            btnAccept.setOnClickListener {
//                var edtTimeBed: EditText
//                edtTimeBed = mAlertDialog.edt_kg
//                var textTimeinterval: String = edtTimeBed!!.text.toString()
//                if (textTimeinterval != "") {
//                    tv_interval_setting.text = "$textTimeinterval min "
//
//                } else {
//                    tv_interval_setting.text = "$time min"
//                }
//                context?.let { it1 ->
//                    AppConfig.setIntervalTime(
//                        tv_interval_setting.text.toString(),
//                        it1
//                    )
//                }
//                mAlertDialog.dismiss()
//            }
            val mDialogView = LayoutInflater.from(context!!).inflate(R.layout.dialog_interval_time, null)

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

            var btnAccept:Button = mDialogView.findViewById(R.id.btn_accept_interval_time)
            val mBuilder = AlertDialog.Builder(context!!)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            var time = AppConfig.getIntervalTime(context!!)
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

            } else {
                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = true

            }
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            var temp=0
            rl_30.setOnClickListener {
                 temp=1
                tgbtn30.isChecked = true
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false
            }
            rl_1.setOnClickListener {
                temp=2
                tgbtn30.isChecked = false
                tgbtn1.isChecked = true
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false
            }
            rl_130.setOnClickListener {
                temp=3
                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = true
                tgbtn2.isChecked = false
                tgbtn3.isChecked = false

            }
            rl_2.setOnClickListener {
                temp=4
                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = true
                tgbtn3.isChecked = false
            }
            rl_3.setOnClickListener {
                temp=5
                tgbtn30.isChecked = false
                tgbtn1.isChecked = false
                tgbtn130.isChecked = false
                tgbtn2.isChecked = false
                tgbtn3.isChecked = true

            }
            btnAccept.setOnClickListener {
                if(temp==1){
                    tv_interval_setting.text = "30 min"
                    AppConfig.setIntervalTime("30 min", context!!)
                }
                else if (temp==2){
                    tv_interval_setting.text = "1 hour"
                    AppConfig.setIntervalTime("60 min", context!!)
                }
                else if (temp==3){
                    tv_interval_setting.text = "90 min"
                    AppConfig.setIntervalTime("90 min", context!!)
                }
                else if (temp==4){
                    AppConfig.setIntervalTime("120 min", context!!)
                    tv_interval_setting.text = "2 hour"
                }else if (temp==5){
                    AppConfig.setIntervalTime("180 min", context!!)
                    tv_interval_setting.text = "3 hour"
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
            btnAccept = mDialogView.findViewById(R.id.btn_accept)

            var tgbtnMale:ToggleButton = mDialogView.findViewById(R.id.tgbtn_male)
            var tgbtnFemale:ToggleButton = mDialogView.findViewById(R.id.tgbtn_female)
            var gender = tvGenderTextView.text
            if(gender=="Male"){
                tgbtnMale!!.isChecked = true
                tgbtnFemale!!.isChecked = false

            }else{
                tgbtnMale!!.isChecked = false
                tgbtnFemale!!.isChecked = true

            }
            mAlertDialog.rl_male.setOnClickListener {
                tgbtnMale!!.isChecked=true
                tgbtnFemale!!.isChecked=false



            }
            mAlertDialog.rl_female.setOnClickListener {
                tgbtnMale!!.isChecked=false
                tgbtnFemale!!.isChecked=true


            }
            btnAccept.setOnClickListener {
                var x = tgbtnMale!!.isChecked
                if(x==true){
                    context?.let { it1 -> AppConfig.setGender("Male", it1) }
                    tv_gender_setting.text="Male"
                }else{
                    context?.let { it1 -> AppConfig.setGender("Female", it1) }
                    tv_gender_setting.text="Female"
                }
                mAlertDialog.dismiss()
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
            val btnAccept:Button = mDialogView.findViewById(R.id.btn_accept)
            btnAccept.setOnClickListener {

                var textKg:String = edtKg!!.text.toString()
                if(textKg!="") {
                    if(textKg == "."){
                        mAlertDialog.tv_title_dialog3.text = "Invalid weight"
                        val handler = Handler()
                        handler.postDelayed({
                            mAlertDialog.tv_title_dialog3.text = "My weight"
                            edtKg.text=null
                        }, 3000)
                    }else{
                    var weight = textKg.toFloat()
                    if(10<weight && weight<200){
                        tv_weight_setting.text = "$textKg kg"
                        var x = tv_weight_setting.text.toString()
                        val y = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                        context?.let { it1 -> AppConfig.setWeight("$y", it1) }
                        var ml = y/0.03
                        val twoDForm = DecimalFormat("#")
                        var xx = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
                        AppConfig.setGoldDrink("$xx", context!!)
                        loadData()
                        mAlertDialog.dismiss()
                    }
                    else{
                        mAlertDialog.tv_title_dialog3.text = "Invalid weight"
                        val handler = Handler()
                        handler.postDelayed({
                            mAlertDialog.tv_title_dialog3.text = "My weight"
                            edtKg.text=null
                        }, 3000)
                    }

                }}
                else{
                    tv_weight_setting.text = "$y kg"
                    var x = tv_weight_setting.text.toString()
                    val y = x!!.replace("[^\\d.]".toRegex(), "").toFloat()
                    context?.let { it1 -> AppConfig.setWeight("$y", it1) }
                    var ml = y/0.03
                    val twoDForm = DecimalFormat("#")
                    var xx = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
                    AppConfig.setGoldDrink("$xx", context!!)
                    mAlertDialog.dismiss()
                }
//                context?.let { it1 -> AppConfig.setWeight(tv_weight_setting.text.toString(), it1) }

            }
        }
        ln_wakeup_time.setOnClickListener {
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_time_select, null)


            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()
            mAlertDialog.setCanceledOnTouchOutside(false)

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
            mAlertDialog.setCanceledOnTouchOutside(false)
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

             var temp=0
            mAlertDialog.rl_reminderoff.setOnClickListener {
                temp=0
                tgbtnRemiberOff.isChecked=true
                tgbtnReminderVibrate.isChecked=false
                tgbtnReminderRing.isChecked=false



            }
            mAlertDialog.rl_reminder_vibrate.setOnClickListener {
                temp=1
                tgbtnRemiberOff.isChecked=false
                tgbtnReminderVibrate.isChecked=true
                tgbtnReminderRing.isChecked=false

            }
            mAlertDialog.rl_reminder_ring.setOnClickListener {
                temp=2
                tgbtnRemiberOff.isChecked=false
                tgbtnReminderVibrate.isChecked=false
                tgbtnReminderRing.isChecked=true

            }
            btnAccept.setOnClickListener {
                if(temp==0){
                    tvReminder.text="Reminder off"
                    context?.let { it1 -> AppConfig.setRemiderMode(0, it1) }
                }else if(temp==1){
                    tvReminder.text="Vibrate only"
                    context?.let { it1 -> AppConfig.setRemiderMode(1, it1) }
                } else if (temp==2){
                    tvReminder.text="Ringtone with vibrate"
                    context?.let { it1 -> AppConfig.setRemiderMode(2, it1) }
                }
                mAlertDialog.dismiss()
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
//        tv_interval_setting.text = context?.let { AppConfig.getIntervalTime(it) }
       var time = AppConfig.getIntervalTime(context!!)
        if(time=="30 min"){
            tv_interval_setting.text="30 min"
        }
        else if(time=="60 min"){
            tv_interval_setting.text="1 hour"
        }
        else if(time=="90 min"){
            tv_interval_setting.text="90 min"
        }
        else if(time=="120 min"){
            tv_interval_setting.text="2 hour"
        }
       else{
            tv_interval_setting.text="3 hour"
        }

        checkbox.isChecked = context?.let { AppConfig.getReminder(it) }!!

        tv_gender_setting.text = context?.let { AppConfig.getGender(it) }
        var y = context?.let { AppConfig.getGoldDrink(it) }
        tv_daily_gold_setting.text = "$y ml"
        var x =context?.let { AppConfig.getWeight(it) }
        tv_weight_setting.text = "$x kg"
        tv_wakeup_time_setting.text = context?.let { AppConfig.getWakeUp(it) }

        tv_bedtime_setting.text = context?.let { AppConfig.getBedTime(it) }
        var z = context?.let { AppConfig.getRemiderMode(it) }
        if(z==0){
            tvReminder.text="Reminder off"
        }
        if(z==1){
            tvReminder.text="Vibrate only"
        }
        if(z==2){
            tvReminder.text="Ringtone with vibrate"
        }
    }

}
