package com.javaindo.lautnusantara.view.result_catch.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.javaindo.lautnusantara.R
import com.javaindo.lautnusantara.view.result_catch.fragment.CalendarFromFragment
import com.javaindo.lautnusantara.view.result_catch.fragment.CalendarToFragment

class DialogCalendarAdapter (val fm : FragmentManager, val tabCount : Int)
    : FragmentPagerAdapter(fm) {


    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                CalendarFromFragment.newInstance()
            }
            1 -> {
                CalendarToFragment.newInstance()
            } else -> {
                CalendarFromFragment.newInstance()
            }
        }
    }


}