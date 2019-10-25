package com.vunhiem.drinkwater.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MonthFragment : Fragment() {
    lateinit var list:ArrayList<Int>
    lateinit var mChart: BarChart
    lateinit var db: DBHelper
    lateinit var tvCompleteMonth:TextView
    lateinit var tvMlWeek:TextView
    lateinit var tvCount:TextView
    var textChart:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_month, container, false)
        mChart = view.findViewById(R.id.barChartMonth)
        // Inflate the layout for this fragment
        tvCompleteMonth = view.findViewById(R.id.tv_completion_month)
        tvMlWeek = view.findViewById(R.id.tv_ml_month)
        tvCount = view.findViewById(R.id.tv_count_month)
        loadDataChart()
        return view
    }

    private fun loadDataChart() {

        var month = ""

        db = context?.let { DBHelper(it) }!!

        var mot = db.getWaterMonth(1).waterdrinkCompletion!!.toInt()
        var hai = db.getWaterMonth(2).waterdrinkCompletion!!.toInt()
        var ba = db.getWaterMonth(3).waterdrinkCompletion!!.toInt()
        var bon = db.getWaterMonth(4).waterdrinkCompletion!!.toInt()
        var nam = db.getWaterMonth(5).waterdrinkCompletion!!.toInt()
        var sau = db.getWaterMonth(6).waterdrinkCompletion!!.toInt()
        var bay = db.getWaterMonth(7).waterdrinkCompletion!!.toInt()
        var tam = db.getWaterMonth(8).waterdrinkCompletion!!.toInt()
        var chin = db.getWaterMonth(9).waterdrinkCompletion!!.toInt()
        var muoi = db.getWaterMonth(10).waterdrinkCompletion!!.toInt()
        var muoi1 = db.getWaterMonth(11).waterdrinkCompletion!!.toInt()
        var muoi2 = db.getWaterMonth(12).waterdrinkCompletion!!.toInt()
        var muoi3 = db.getWaterMonth(13).waterdrinkCompletion!!.toInt()
        var muoi4 = db.getWaterMonth(14).waterdrinkCompletion!!.toInt()
        var muoi5 = db.getWaterMonth(15).waterdrinkCompletion!!.toInt()
        var muoi6 = db.getWaterMonth(16).waterdrinkCompletion!!.toInt()
        var muoi7 = db.getWaterMonth(17).waterdrinkCompletion!!.toInt()
        var muoi8 = db.getWaterMonth(18).waterdrinkCompletion!!.toInt()
        var muoi9 = db.getWaterMonth(19).waterdrinkCompletion!!.toInt()
        var haimuoi = db.getWaterMonth(20).waterdrinkCompletion!!.toInt()
        var hai1 = db.getWaterMonth(21).waterdrinkCompletion!!.toInt()
        var hai2 = db.getWaterMonth(22).waterdrinkCompletion!!.toInt()
        var hai3 = db.getWaterMonth(23).waterdrinkCompletion!!.toInt()
        var hai4 = db.getWaterMonth(24).waterdrinkCompletion!!.toInt()
        var hai5 = db.getWaterMonth(25).waterdrinkCompletion!!.toInt()
        var hai6 = db.getWaterMonth(26).waterdrinkCompletion!!.toInt()
        var hai7 = db.getWaterMonth(27).waterdrinkCompletion!!.toInt()
        var hai8 = db.getWaterMonth(28).waterdrinkCompletion!!.toInt()
        var hai9 = db.getWaterMonth(29).waterdrinkCompletion!!.toInt()
        var bamuoi = db.getWaterMonth(30).waterdrinkCompletion!!.toInt()
        var ba1 = db.getWaterMonth(31).waterdrinkCompletion!!.toInt()


        list = ArrayList()
        list.add(mot)
        list.add(hai)
        list.add(ba)
        list.add(bon)
        list.add(nam)
        list.add(sau)
        list.add(bay)
        list.add(tam)
        list.add(chin)
        list.add(muoi)
        list.add(muoi1)
        list.add(muoi2)
        list.add(muoi3)
        list.add(muoi4)
        list.add(muoi5)
        list.add(muoi6)
        list.add(muoi7)
        list.add(muoi8)
        list.add(muoi9)
        list.add(haimuoi)
        list.add(hai1)
        list.add(hai2)
        list.add(hai3)
        list.add(hai4)
        list.add(hai5)
        list.add(hai6)
        list.add(hai7)
        list.add(hai8)
        list.add(hai9)
        list.add(bamuoi)
        var count = 30
        for (i in 0..29) {
            if (list[i] == 0) {
                count--
            }
        }
        if(count != 0)
        {
           var comPletion = 0
            for (i in 0..29){
                comPletion+=list[i]
            }


            var comPletionTBC = comPletion/count
            tvCompleteMonth.text = "$comPletionTBC %"
            db = DBHelper(context!!)
            var x = db.getAllWaterMonth()
            var waterTBC = x/count
            tvMlWeek.text = "$waterTBC ml"

            var countDrink = db.getAllCountMonth()
            var countTBC = countDrink/count
            tvCount.text = "$countTBC /Day"
            textChart ="Month"
        }else{
            tvCount.text = "0 /Day"
            tvMlWeek.text = "0 ml"
            tvCompleteMonth.text = "0 %"
            textChart = "Data will saved at end of the day"
        }





        mChart.description.isEnabled = false
        mChart.setDrawBarShadow(false)
        mChart.setDrawValueAboveBar(false)
        mChart.setPinchZoom(false)
        mChart.setDrawGridBackground(false)
        mChart.getAxisLeft().setStartAtZero(true)
        mChart.getAxisRight().setEnabled(false)
        val barEntries: ArrayList<BarEntry> = ArrayList()
        barEntries.add(BarEntry(0.toFloat(), mot!!.toFloat()))
        barEntries.add(BarEntry(1.toFloat(), hai!!.toFloat()))
        barEntries.add(BarEntry(2.toFloat(), ba!!.toFloat()))
        barEntries.add(BarEntry(3.toFloat(), bon!!.toFloat()))
        barEntries.add(BarEntry(4.toFloat(), nam!!.toFloat()))
        barEntries.add(BarEntry(5.toFloat(), sau!!.toFloat()))
        barEntries.add(BarEntry(6.toFloat(), bay!!.toFloat()))
        barEntries.add(BarEntry(7.toFloat(), tam!!.toFloat()))
        barEntries.add(BarEntry(8.toFloat(), chin!!.toFloat()))
        barEntries.add(BarEntry(9.toFloat(), muoi!!.toFloat()))
        barEntries.add(BarEntry(10.toFloat(), muoi1!!.toFloat()))
        barEntries.add(BarEntry(11.toFloat(), muoi2!!.toFloat()))
        barEntries.add(BarEntry(12.toFloat(), muoi3!!.toFloat()))
        barEntries.add(BarEntry(13.toFloat(), muoi4!!.toFloat()))
        barEntries.add(BarEntry(14.toFloat(), muoi5!!.toFloat()))
        barEntries.add(BarEntry(15.toFloat(), muoi6!!.toFloat()))
        barEntries.add(BarEntry(16.toFloat(), muoi7!!.toFloat()))
        barEntries.add(BarEntry(17.toFloat(), muoi8!!.toFloat()))
        barEntries.add(BarEntry(18.toFloat(), muoi9!!.toFloat()))
        barEntries.add(BarEntry(19.toFloat(), haimuoi!!.toFloat()))
        barEntries.add(BarEntry(20.toFloat(), hai1!!.toFloat()))
        barEntries.add(BarEntry(21.toFloat(), hai2!!.toFloat()))
        barEntries.add(BarEntry(22.toFloat(), hai3!!.toFloat()))
        barEntries.add(BarEntry(23.toFloat(), hai4!!.toFloat()))
        barEntries.add(BarEntry(24.toFloat(), hai5!!.toFloat()))
        barEntries.add(BarEntry(25.toFloat(), hai6!!.toFloat()))
        barEntries.add(BarEntry(26.toFloat(), hai7!!.toFloat()))
        barEntries.add(BarEntry(27.toFloat(), hai8!!.toFloat()))
        barEntries.add(BarEntry(28.toFloat(), hai9!!.toFloat()))
        barEntries.add(BarEntry(29.toFloat(), bamuoi!!.toFloat()))
        barEntries.add(BarEntry(30.toFloat(), ba1!!.toFloat()))

        val barDataSet = BarDataSet(barEntries, textChart)
        val data = BarData(barDataSet)

        data.setDrawValues(false)
        data.barWidth = 0.3f
//        setData()
        val weeks = arrayOf(
            "1",
            "",
            "",
            "",
            "",
            "",
            "",
            "8",
            "",
            "",
            "",
            "",
            "",
            "",
            "15",
            "",
            "",
            "",
            "",
            "",
            "",
            "22",
            "",
            "",
            "",
            "",
            "",
            "",
            "29",
            "",
            ""
        )
        mChart.data = data
        val xAxis: XAxis = mChart.xAxis
        xAxis.textSize = 9F
        xAxis.valueFormatter = MyXAxisValueFormater(weeks)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setLabelCount(31)
        xAxis.setAvoidFirstLastClipping(true)
        mChart.getXAxis().setDrawGridLines(false)
        mChart.getAxisLeft().setDrawGridLines(true)
        mChart.getAxisRight().setDrawGridLines(true)


    }


    inner class MyXAxisValueFormater(private val mValues: Array<String>) :
        IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValues[value.toInt()]
        }
    }

}



