package com.vunhiem.drinkwater.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import com.vunhiem.drinkwater.R
import kotlinx.android.synthetic.main.fragment_history.*


class HistoryFragment : Fragment() {
     lateinit var tgToday:ToggleButton
    lateinit var tgWeek:ToggleButton
    lateinit var tgMonth:ToggleButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_history, container, false)
        tgToday = view.findViewById(R.id.tgbtn_today)
        tgWeek = view.findViewById(R.id.tgbtn_week)
        tgMonth= view.findViewById(R.id.tgbtn_month)
        loadData()
        onClick()
        // Inflate the layout for this fragment
        return view

    }

    private fun onClick() {
        tgToday.setOnClickListener {
            val fragment = TodayFragment()
            addFragment(fragment)
            tgToday.isChecked=true
            tgWeek.isChecked=false
            tgMonth.isChecked= false
        }
        tgWeek.setOnClickListener {
            val fragment1 = WeekFragment()
            addFragment(fragment1)
            tgToday.isChecked=false
            tgWeek.isChecked=true
            tgMonth.isChecked= false
        }
        tgMonth.setOnClickListener {
            val fragment2 = MonthFragment()
            addFragment(fragment2)
            tgToday.isChecked=false
            tgWeek.isChecked=false
            tgMonth.isChecked= true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    private fun loadData() {
        val fragment = TodayFragment()
        addFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(com.vunhiem.drinkwater.R.id.container2, fragment, fragment.javaClass.getSimpleName())
            ?.commit()
    }
}
