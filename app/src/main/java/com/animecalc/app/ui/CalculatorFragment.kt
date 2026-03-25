package com.animecalc.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.animecalc.app.databinding.FragmentCalculatorBinding
import com.animecalc.app.viewmodel.CalculatorViewModel

class CalculatorFragment : Fragment() {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculatorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDisplay()
        setupNumberPad()
        setupOperations()
        setupSpecial()
    }

    private fun setupDisplay() {
        viewModel.displayText.observe(viewLifecycleOwner) { text ->
            binding.displayEdit.setText(text)
        }
    }

    private fun setupNumberPad() {
        val numberButtons = mapOf(
            binding.btn0 to '0',
            binding.btn1 to '1',
            binding.btn2 to '2',
            binding.btn3 to '3',
            binding.btn4 to '4',
            binding.btn5 to '5',
            binding.btn6 to '6',
            binding.btn7 to '7',
            binding.btn8 to '8',
            binding.btn9 to '9',
            binding.btnDot to '.'
        )

        numberButtons.forEach { (button, digit) ->
            button.setOnClickListener {
                animateButtonPress(it)
                viewModel.appendDigit(digit)
            }
        }
    }

    private fun setupOperations() {
        val operationButtons = mapOf(
            binding.btnPlus to '+',
            binding.btnMinus to '-',
            binding.btnMultiply to '*',
            binding.btnDivide to '/'
        )

        operationButtons.forEach { (button, op) ->
            button.setOnClickListener {
                animateButtonPress(it)
                viewModel.setOperation(op)
            }
        }
    }

    private fun setupSpecial() {
        binding.btnEquals.setOnClickListener {
            animateButtonPress(it)
            viewModel.equals()
        }

        binding.btnClear.setOnClickListener {
            animateButtonPress(it)
            viewModel.clear()
        }

        binding.btnPercent.setOnClickListener {
            animateButtonPress(it)
            viewModel.percent()
        }

        binding.btnBackspace.setOnClickListener {
            animateButtonPress(it)
            viewModel.backspace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
