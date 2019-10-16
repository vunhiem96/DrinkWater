package com.vunhiem.drinkwater.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.vunhiem.drinkwater.Model.Weight
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper
import com.vunhiem.drinkwater.screen.dailywatermain.SetWeightActivity
import com.vunhiem.drinkwater.screen.settingweight.SettingWeight
import com.vunhiem.drinkwater.utils.AppConfig
import kotlinx.android.synthetic.main.fragment_weight.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class WeightFragment : Fragment() {
    lateinit var imgSetWeight:LinearLayout
    lateinit var mChart: LineChart
    lateinit var tgWeek: ToggleButton
    lateinit var tgMonth: ToggleButton
    lateinit var tgYear: ToggleButton
    lateinit var tvWeight:TextView
    lateinit var tvBmi:TextView
    lateinit var imgBmiSmall1:ImageView
    lateinit var imgBmiSmall2:ImageView
    lateinit var imgBmiNormal:ImageView
    lateinit var imgBmiFat:ImageView
    lateinit var tvTarget:TextView
    lateinit var tvReminder:TextView
    lateinit var imgSetHeight:ImageView
    lateinit var db:DBHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_weight, container, false)
        mChart = view.findViewById(R.id.lineChart)
        tgWeek = view.findViewById(R.id.tgbtn_week)
        tgMonth = view.findViewById(R.id.tgbtn_month)
        tgYear = view.findViewById(R.id.tgbtn_year)
        imgSetWeight = view.findViewById(R.id.ln_addweight)
        tvWeight = view.findViewById(R.id.tv_kg_weightfragment)
        tvBmi = view.findViewById(R.id.tv_bmi)
        imgBmiSmall1= view.findViewById(R.id.imb_bmi_small)
        imgBmiSmall2 = view.findViewById(R.id.imb_bmi_small2)
        imgBmiNormal = view.findViewById(R.id.img_bmi_normal)
        imgBmiFat = view.findViewById(R.id.bmi_fat)
        tvTarget = view.findViewById(R.id.tv_target_kg)
        tvReminder = view.findViewById(R.id.tv_reminder_weight)
        imgSetHeight = view.findViewById(R.id.setweight)
        dataBase()
        loadDataChart()
        onCick()
        loadData()
        return view
    }

    private fun dataBase() {
        db = context?.let { DBHelper(it) }!!
        db.createDefaultWeightWeek()
        db.createDefaultWeightMonth()
        db.createDefaultWeightYear()
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        var weight = context?.let { AppConfig.getWeight(it) }
        var weightAdd = weight!!.replace("[^\\d.]".toRegex(), "").toFloat()
        if(day == Calendar.MONDAY){
            db.updateWeightWeek(Weight(2, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.TUESDAY){
            db.updateWeightWeek(Weight(3, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.WEDNESDAY){
            db.updateWeightWeek(Weight(4, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.THURSDAY){
            db.updateWeightWeek(Weight(5, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.FRIDAY){
            db.updateWeightWeek(Weight(6, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.SATURDAY){
            db.updateWeightWeek(Weight(7, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()

            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            Log.i("vcvcff","$currentDate2")
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
        else if(day == Calendar.SUNDAY){
            db.updateWeightWeek(Weight(8, weightAdd))
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val weightMonth = Weight(currentDate, weightAdd)
            db.updateWeightMonth(weightMonth)

            val sdf2 = SimpleDateFormat("MM")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            val weightYear = Weight(currentDate2, weightAdd)
            db.updateWeightYear(weightYear)
        }
    }


    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        var weight = context?.let { AppConfig.getWeight(it) }
        var weightText = weight!!.replace("[^\\d.]".toRegex(), "").toFloat()

        tvWeight.text= weightText.toString()

        var weightBMI = weight!!.replace("[^\\d.]".toRegex(), "").toFloat()
        var height = context?.let { AppConfig.getHeight(it) }
        var height2 = height!!.replace("[^\\d.]".toRegex(), "").toFloat()


        var heightMBI = height2 * 0.01

        var xx = weightBMI/(heightMBI * heightMBI)
        val twoDForm = DecimalFormat("#.#")
//        var BMI = java.lang.Double.valueOf(twoDForm.format(xx))
        var z = xx * 100
        var BMI2 = Math.round(z)
        var BMI:Float = (BMI2 / 100f)

        var heightLe = height2 - 100
        var targetWeight = ((heightLe *9)/10)
        var taget1 = targetWeight*10
        var taget2 = Math.round(taget1)
        var targetWeight2 = taget2/10
        val twoDForm2 = DecimalFormat("#.#")
//
//        var targetWeight2 = java.lang.Double.valueOf(twoDForm2.format(targetWeight))


        var checkWeight = context?.let { AppConfig.getTargetWeight(it) }
        if(checkWeight ==""){
        tvTarget.text = "$targetWeight2 kg"
            context?.let { AppConfig.setTargetWeight("$targetWeight2", it) }
            Log.i("vcvccv","$targetWeight2")
            Log.i("vcvccv","$checkWeight")
            var remind = weightText - targetWeight2
            var remind2 = Math.abs(remind)
            var remind3 = java.lang.Double.valueOf(twoDForm2.format(remind2))
            tvReminder.text="$remind3 kg"
        }else{
            tvTarget.text = "$checkWeight kg"
            var target = checkWeight!!.toFloat()
            var remind = weightText - target
            var remind2 = Math.abs(remind)
            var z = remind2 * 100
            var reminder3 = Math.round(z)
            var reminder4 = (reminder3 / 100f)
//            var remind3 = java.lang.Double.valueOf(twoDForm2.format(remind2))
            tvReminder.text="$reminder4 kg"
        }




        if(BMI<18.5){
            imgBmiSmall1.isVisible=true
            imgBmiSmall2.isInvisible=true
            imgBmiNormal.isInvisible=true
            imgBmiFat.isInvisible=true
            tvBmi.text = "$BMI (Underweight)"
        }
        else if(BMI<25 && BMI>=18.5){
            imgBmiSmall1.isInvisible=true
            imgBmiSmall2.isVisible=true
            imgBmiNormal.isInvisible=true
            imgBmiFat.isInvisible=true
            tvBmi.text = "$BMI (Normal)"
        }
        else if(BMI<=29.9 && BMI>=25){
            imgBmiSmall1.isInvisible=true
            imgBmiSmall2.isInvisible=true
            imgBmiNormal.isVisible=true
            imgBmiFat.isInvisible=true
            tvBmi.text = "$BMI (Overweight)"
        }
        else if( BMI>=30){
            imgBmiSmall1.isInvisible=true
            imgBmiSmall2.isInvisible=true
            imgBmiNormal.isInvisible=true
            imgBmiFat.isVisible=true
            tvBmi.text = "$BMI (Obesity)"
        }



    }


    private fun onCick() {
        imgSetHeight.setOnClickListener {
            val intent = Intent(context, SettingWeight::class.java)
            startActivity(intent)
        }
        imgSetWeight.setOnClickListener {
            val intent = Intent(context, SetWeightActivity::class.java)
            var text = tv_kg_weightfragment.text.toString()
            intent.putExtra("weight", text)
            startActivity(intent)
        }
        tgWeek.setOnClickListener {
            mChart.isInvisible= false
            tgWeek.isChecked = true
            tgMonth.isChecked = false
            tgYear.isChecked = false
            fragmentManager!!.beginTransaction()
                .remove(fragmentManager!!.findFragmentById(R.id.container3)!!).commit()


        }
        tgMonth.setOnClickListener {
            mChart.isInvisible= true
            tgWeek.isChecked = false
            tgMonth.isChecked = true
            tgYear.isChecked = false
            val fragment = ChartMonthFragment()
            addFragment(fragment)
        }
        tgYear.setOnClickListener {
            mChart.isInvisible= true
            tgWeek.isChecked = false
            tgMonth.isChecked = false
            tgYear.isChecked = true
            val fragment2 = ChartYearFragment()
            addFragment(fragment2)
        }
    }


    private fun loadDataChart() {
        mChart.description.isEnabled = false
        mChart.setPinchZoom(false)
        mChart.setDrawGridBackground(false)
        mChart.isDragEnabled = true
        mChart.setScaleEnabled(false)
        val barEntries: ArrayList<Entry> = ArrayList()

        barEntries.add(Entry(0.toFloat(), db.getWeightDay(2).weight!!.toFloat()))
        barEntries.add(Entry(1.toFloat(), db.getWeightDay(3).weight!!.toFloat()))
        barEntries.add(Entry(2.toFloat(), db.getWeightDay(4).weight!!.toFloat()))
        barEntries.add(Entry(3.toFloat(), db.getWeightDay(5).weight!!.toFloat()))
        barEntries.add(Entry(4.toFloat(), db.getWeightDay(6).weight!!.toFloat()))
        barEntries.add(Entry(5.toFloat(), db.getWeightDay(7).weight!!.toFloat()))
        barEntries.add(Entry(6.toFloat(), db.getWeightDay(8).weight!!.toFloat()))

        val lineDataSet = LineDataSet(barEntries, "Weeks")
        lineDataSet.setDrawValues(false)
        val weeks = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sar", "Sun")
        lineDataSet.fillAlpha = 110
        var dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)
        val data = LineData(dataSets)
        mChart.data = data
        mChart.getXAxis().setDrawGridLines(false)
        mChart.getAxisLeft().setDrawGridLines(true)
        mChart.getAxisRight().setDrawGridLines(true)

        mChart.data.isHighlightEnabled = false
        val xAxis: XAxis = mChart.xAxis
        xAxis.valueFormatter = MyXAxisValueFormater(weeks)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    inner class MyXAxisValueFormater(private val mValues: Array<String>) :
        IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValues[value.toInt()]
        }
    }

    private fun addFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(
                com.vunhiem.drinkwater.R.id.container3,
                fragment,
                fragment.javaClass.getSimpleName()
            )
            ?.commit()
    }
}
