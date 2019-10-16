package com.vunhiem.drinkwater.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper


class WeekFragment : Fragment() {
    lateinit var tvMl:TextView
    lateinit var tvComplete:TextView
    lateinit var mChart: BarChart
    lateinit var db:DBHelper
    lateinit var list:ArrayList<Int>
    lateinit var tvCount:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(com.vunhiem.drinkwater.R.layout.fragment_week, container, false)
        mChart = view.findViewById(com.vunhiem.drinkwater.R.id.barChart)
        tvMl = view.findViewById(R.id.tv_ml_week)
        tvCount = view.findViewById(R.id.tv_count)
        tvComplete = view.findViewById(R.id.tv_completion)
        loadDataChart()
        return view

    }


    private fun loadDataChart() {
        db = context?.let { DBHelper(it) }!!
        var hai = db.getWaterDay(2)
        var ba = db.getWaterDay(3)
        var tu = db.getWaterDay(4)
        var nam = db.getWaterDay(5)
        var sau = db.getWaterDay(6)
        var bay = db.getWaterDay(7)
        var cn = db.getWaterDay(8)
        var x = nam.waterdrinkCompletion
        var y = sau.waterdrinkCompletion
        Log.i("bjbjbjbj","$x")
        Log.i("bjbjbjbj","$y")

        var haiml = hai.waterdrink
        var baml = ba.waterdrink
        var tuml = tu.waterdrink
        var namml = nam.waterdrink
        var sauml = sau.waterdrink
        var bayml = bay.waterdrink
        list = ArrayList()
        list.add(haiml!!)
        list.add(baml!!)
        list.add(tuml!!)
        list.add(namml!!)
        list.add(sauml!!)
        list.add(bayml!!)
        var count = 6
        for (i in 0..5){
          if(list[i] == 0){
              count--
          }
        }
        var haicount = hai.waterDrinkCount
        var bacount = ba.waterDrinkCount
        var tucount= tu.waterDrinkCount
        var namcount = nam.waterDrinkCount
        var saucount = sau.waterDrinkCount
        var baycount = bay.waterDrinkCount

        var haiCom = hai.waterdrinkCompletion
        var baCom = ba.waterdrinkCompletion
        var tuCom= tu.waterdrinkCompletion
        var namCom = nam.waterdrinkCompletion
        var sauCom = sau.waterdrinkCompletion
        var bayCom = bay.waterdrinkCompletion
         if(count != 0){
             var countTBC = (haicount + bacount + tucount + namcount + saucount + baycount) / count
             var waterTBC = (haiml + baml + tuml + namml + sauml + bayml) / count
             var completeTBC =
                 (haiCom!! + baCom!! + tuCom!! + namCom!! + sauCom!! + bayCom!!) / count
             tvCount.text = "$countTBC /Day"
             tvMl.text = "$waterTBC ml"
             tvComplete.text = "$completeTBC %"
         }else{
             tvCount.text = "0 /Day"
             tvMl.text = "0 ml"
             tvComplete.text = "0 %"
         }




        mChart.description.isEnabled = false
        mChart.setDrawBarShadow(false)
        mChart.setDrawValueAboveBar(false)
        mChart.setPinchZoom(false)
        mChart.setDrawGridBackground(false)
        val barEntries:ArrayList<BarEntry> = ArrayList()
        barEntries.add(BarEntry(0.toFloat(), hai.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(1.toFloat(), ba.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(2.toFloat(), tu.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(3.toFloat(), nam.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(4.toFloat(), sau.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(5.toFloat(), bay.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(6.toFloat(), cn.waterdrinkCompletion!!.toFloat()))

        val barDataSet = BarDataSet(barEntries, "Weeks")
        val data= BarData(barDataSet)
        data.barWidth=0.5f
        data.setDrawValues(false)
//        setData()
        val weeks = arrayOf("Mon", "Tue","Wed","Thu","Fri","Sar","Sun")
        mChart.data=data
        val xAxis:XAxis = mChart.xAxis
        xAxis.valueFormatter=MyXAxisValueFormater(weeks)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity= 1.toFloat()
        mChart.data.isHighlightEnabled = false
        mChart.invalidate()
        mChart.getXAxis().setDrawGridLines(false)
        mChart.getAxisLeft().setDrawGridLines(true)
        mChart.getAxisRight().setDrawGridLines(true)


    }


    inner class MyXAxisValueFormater(private val mValues: Array<String>):
        IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValues[value.toInt()]
        }
    }
    }


//    private fun setData() {
//        var yVals: ArrayList<BarEntry> = ArrayList()
//        for (i in 0 until 8) {
////            var value: Float = ((Math.random() * 100).toFloat())
//            yVals.add(BarEntry(i.toFloat(), 100.toFloat()))
//
//        }
//        var set=BarDataSet(yVals,"Weeks")
//        set.setDrawValues(true)
//        var data = BarData(set)
//        mChart.data=data
//        mChart.invalidate()
//        mChart.animateY(300)
//
//    }



