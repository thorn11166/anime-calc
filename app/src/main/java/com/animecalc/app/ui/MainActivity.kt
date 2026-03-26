package com.animecalc.app.ui

import android.os.Bundle
import android.view.View
import android.view.animation.ScaleAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.animecalc.app.R
import com.animecalc.app.databinding.ActivityMainBinding
import com.animecalc.app.viewmodel.CalculatorViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

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
        val pagerAdapter = MainPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        // Connect TabLayout with ViewPager2 using TabLayoutMediator
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Calculator"
                1 -> "History"
                2 -> "Settings"
                else -> ""
            }
        }.attach()
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
