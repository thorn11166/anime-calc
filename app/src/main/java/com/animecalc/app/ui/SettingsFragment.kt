package com.animecalc.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.animecalc.app.BuildConfig
import com.animecalc.app.databinding.FragmentSettingsBinding
import com.animecalc.app.viewmodel.CalculatorViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculatorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.versionText.text = "Version: ${BuildConfig.VERSION_NAME}"

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleDarkMode()
        }

        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDark ->
            binding.darkModeSwitch.isChecked = isDark
        }

        binding.btnClearHistory.setOnClickListener {
            animateButtonPress(it)
            viewModel.clearHistory()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
