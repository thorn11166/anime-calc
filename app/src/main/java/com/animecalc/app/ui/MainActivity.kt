package com.animecalc.app.ui

import android.os.Bundle
import android.view.View
import android.view.animation.ScaleAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.animecalc.app.R
import com.animecalc.app.databinding.ActivityMainBinding
import com.animecalc.app.viewmodel.CalculatorViewModel
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        setupUI()
        setupObservers()
        applyTheme()
    }

    private fun setupUI() {
        val pagerAdapter = MainPagerAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.viewPager.adapter = pagerAdapter

        binding.tabLayout.apply {
            addTab(newTab().apply { text = "Calculator" })
            addTab(newTab().apply { text = "History" })
            addTab(newTab().apply { text = "Settings" })
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.viewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupObservers() {
        viewModel.isDarkMode.observe(this) { isDark ->
            applyTheme()
        }
    }

    private fun applyTheme() {
        val isDark = viewModel.isDarkMode.value ?: false
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}

fun animateButtonPress(view: View) {
    val animation = ScaleAnimation(
        1f, 0.95f, 1f, 0.95f,
        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
        ScaleAnimation.RELATIVE_TO_SELF, 0.5f
    ).apply {
        duration = 100
        repeatCount = 1
        repeatMode = android.view.animation.Animation.REVERSE
    }
    view.startAnimation(animation)
}
