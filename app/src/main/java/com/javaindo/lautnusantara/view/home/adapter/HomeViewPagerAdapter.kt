package com.javaindo.lautnusantara.view.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.javaindo.lautnusantara.view.home.ui.account.AccountFragment
import com.javaindo.lautnusantara.view.home.ui.helpcenter.HelpCenterFragment
import com.javaindo.lautnusantara.view.home.ui.home.HomeFragment
import com.javaindo.lautnusantara.view.home.ui.information.InformationFragment

class HomeViewPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment.newInstance()
            1 -> HelpCenterFragment.newInstance()
            2 -> InformationFragment.newInstance()
            3 -> AccountFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }


}