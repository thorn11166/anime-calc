package com.animecalc.app.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.animecalc.app.ui.CalculatorFragment
import com.animecalc.app.ui.HistoryFragment
import com.animecalc.app.ui.SettingsFragment

class MainPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> CalculatorFragment()
        1 -> HistoryFragment()
        2 -> SettingsFragment()
        else -> CalculatorFragment()
    }
}
