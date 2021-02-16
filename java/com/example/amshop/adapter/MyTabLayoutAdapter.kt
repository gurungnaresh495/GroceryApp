package com.example.amshop.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

class MyTabLayoutAdapter(var fm: FragmentManager): FragmentPagerAdapter(fm) {

    var fragmentList = ArrayList<Fragment>()
    var titleList = ArrayList<String>()

    override fun getCount(): Int {
       return titleList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment()
    {

    }

}