package com.animecalc.app.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> CalculatorFragment()
        1 -> HistoryFragment()
        2 -> SettingsFragment()
        else -> CalculatorFragment()
    }
}
