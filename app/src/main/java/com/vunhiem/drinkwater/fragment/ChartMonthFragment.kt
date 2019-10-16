package com.vunhiem.drinkwater.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper

/**
 * A simple [Fragment] subclass.
 */
class ChartMonthFragment : Fragment() {
    lateinit var mChart: LineChart
    lateinit var db:DBHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_chart_month, container, false)
        mChart = view.findViewById(R.id.lineCharMonth)
        loadDataChartMonth()
        return view
    }

    private fun loadDataChartMonth() {
        db = context?.let { DBHelper(it) }!!
        mChart.description.isEnabled = false
        mChart.setPinchZoom(false)
        mChart.setDrawGridBackground(false)
        mChart.isDragEnabled = true
        mChart.setScaleEnabled(false)
        val barEntries2: ArrayList<Entry> = ArrayList()
        barEntries2.add(Entry(0.toFloat(), db.getWeightMonth(1).weight!!.toFloat()))
        barEntries2.add(Entry(1.toFloat(), db.getWeightMonth(2).weight!!.toFloat()))
        barEntries2.add(Entry(2.toFloat(), db.getWeightMonth(3).weight!!.toFloat()))
        barEntries2.add(Entry(3.toFloat(), db.getWeightMonth(4).weight!!.toFloat()))
        barEntries2.add(Entry(4.toFloat(), db.getWeightMonth(5).weight!!.toFloat()))
        barEntries2.add(Entry(5.toFloat(), db.getWeightMonth(6).weight!!.toFloat()))
        barEntries2.add(Entry(6.toFloat(), db.getWeightMonth(7).weight!!.toFloat()))
        barEntries2.add(Entry(7.toFloat(), db.getWeightMonth(8).weight!!.toFloat()))
        barEntries2.add(Entry(8.toFloat(), db.getWeightMonth(9).weight!!.toFloat()))
        barEntries2.add(Entry(9.toFloat(), db.getWeightMonth(10).weight!!.toFloat()))
        barEntries2.add(Entry(10.toFloat(), db.getWeightMonth(11).weight!!.toFloat()))
        barEntries2.add(Entry(11.toFloat(), db.getWeightMonth(12).weight!!.toFloat()))
        barEntries2.add(Entry(12.toFloat(), db.getWeightMonth(13).weight!!.toFloat()))
        barEntries2.add(Entry(13.toFloat(), db.getWeightMonth(14).weight!!.toFloat()))
        barEntries2.add(Entry(14.toFloat(), db.getWeightMonth(15).weight!!.toFloat()))
        barEntries2.add(Entry(15.toFloat(), db.getWeightMonth(16).weight!!.toFloat()))
        barEntries2.add(Entry(16.toFloat(), db.getWeightMonth(17).weight!!.toFloat()))
        barEntries2.add(Entry(17.toFloat(), db.getWeightMonth(18).weight!!.toFloat()))
        barEntries2.add(Entry(18.toFloat(), db.getWeightMonth(19).weight!!.toFloat()))
        barEntries2.add(Entry(19.toFloat(), db.getWeightMonth(20).weight!!.toFloat()))
        barEntries2.add(Entry(20.toFloat(), db.getWeightMonth(21).weight!!.toFloat()))
        barEntries2.add(Entry(21.toFloat(), db.getWeightMonth(22).weight!!.toFloat()))
        barEntries2.add(Entry(22.toFloat(), db.getWeightMonth(23).weight!!.toFloat()))
        barEntries2.add(Entry(23.toFloat(), db.getWeightMonth(24).weight!!.toFloat()))
        barEntries2.add(Entry(24.toFloat(), db.getWeightMonth(25).weight!!.toFloat()))
        barEntries2.add(Entry(25.toFloat(), db.getWeightMonth(26).weight!!.toFloat()))
        barEntries2.add(Entry(26.toFloat(), db.getWeightMonth(27).weight!!.toFloat()))
        barEntries2.add(Entry(27.toFloat(), db.getWeightMonth(28).weight!!.toFloat()))
        barEntries2.add(Entry(28.toFloat(), db.getWeightMonth(29).weight!!.toFloat()))
        barEntries2.add(Entry(29.toFloat(), db.getWeightMonth(30).weight!!.toFloat()))
        barEntries2.add(Entry(30.toFloat(), db.getWeightMonth(31).weight!!.toFloat()))

        val lineDataSet3 = LineDataSet(barEntries2, "Month")
        lineDataSet3.setDrawValues(false)
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
        lineDataSet3.fillAlpha = 110
        var dataSets3: ArrayList<ILineDataSet> = ArrayList()
        dataSets3.add(lineDataSet3)
        val data3 = LineData(dataSets3)
        mChart.data = data3
        mChart.getXAxis().setDrawGridLines(false)
        mChart.getAxisLeft().setDrawGridLines(true)
        mChart.getAxisRight().setDrawGridLines(true)

        mChart.data.isHighlightEnabled = false
        val xAxis2: XAxis = mChart.xAxis
        xAxis2.valueFormatter = MyXAxisValueFormater2(weeks)
        xAxis2.position = XAxis.XAxisPosition.BOTTOM
        xAxis2.setLabelCount(31)
    }
    inner class MyXAxisValueFormater2(private val mValues: Array<String>) :
        IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValues[value.toInt()]
        }
    }
}
