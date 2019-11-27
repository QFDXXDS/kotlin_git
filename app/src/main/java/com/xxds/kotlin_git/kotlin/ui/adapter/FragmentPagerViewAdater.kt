package com.xxds.kotlin_git.kotlin.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class  FragmentPagerViewAdater(private  val fragmentList: List<Fragment>, supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager) {


    override fun getCount(): Int = fragmentList.size

    override fun getItem(p0: Int): Fragment {

        return fragmentList[p0]
    }


}