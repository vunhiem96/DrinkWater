package com.vunhiem.drinkwater.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

import com.vunhiem.drinkwater.R
import com.vunhiem.drinkwater.db.DBHelper

/**
 * A simple [Fragment] subclass.
 */
class ChartYearFragment : Fragment() {
    lateinit var mChart: LineChart
    lateinit var db:DBHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_chart_year, container, false)
        // Inflate the layout for this fragment
        mChart = view.findViewById(R.id.lineCharYear)
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

        val barEntries: ArrayList<Entry> = ArrayList()
        barEntries.add(Entry(0.toFloat(), db.getWeightYear(1).weight!!.toFloat()))
        barEntries.add(Entry(1.toFloat(), db.getWeightYear(2).weight!!.toFloat()))
        barEntries.add(Entry(2.toFloat(), db.getWeightYear(3).weight!!.toFloat()))
        barEntries.add(Entry(3.toFloat(), db.getWeightYear(4).weight!!.toFloat()))
        barEntries.add(Entry(4.toFloat(), db.getWeightYear(5).weight!!.toFloat()))
        barEntries.add(Entry(5.toFloat(), db.getWeightYear(6).weight!!.toFloat()))
        barEntries.add(Entry(6.toFloat(), db.getWeightYear(7).weight!!.toFloat()))
        barEntries.add(Entry(7.toFloat(), db.getWeightYear(8).weight!!.toFloat()))
        barEntries.add(Entry(8.toFloat(), db.getWeightYear(9).weight!!.toFloat()))
        barEntries.add(Entry(9.toFloat(), db.getWeightYear(10).weight!!.toFloat()))
        barEntries.add(Entry(10.toFloat(), db.getWeightYear(11).weight!!.toFloat()))
        barEntries.add(Entry(11.toFloat(), db.getWeightYear(12).weight!!.toFloat()))


        val lineDataSet = LineDataSet(barEntries, "Year")
        lineDataSet.setDrawValues(false)
        val weeks = arrayOf("1", "2", "3", "4", "5", "6", "7","8","9","10","11","12")
        lineDataSet.fillAlpha = 110
        var dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)
        val data = LineData(dataSets)
        mChart.data = data
        mChart.getXAxis().setDrawGridLines(false)
        mChart.getAxisLeft().setDrawGridLines(true)
        mChart.getAxisRight().setDrawGridLines(true)
        mChart.getAxisRight().setEnabled(false)

        mChart.data.isHighlightEnabled = false
        val xAxis: XAxis = mChart.xAxis
        xAxis.valueFormatter = MyXAxisValueFormater(weeks)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setLabelCount(11)
    }

    inner class MyXAxisValueFormater(private val mValues: Array<String>) :
        IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValues[value.toInt()]
        }
    }
}



