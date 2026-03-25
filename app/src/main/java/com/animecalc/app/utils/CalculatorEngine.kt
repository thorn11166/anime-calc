package com.animecalc.app.utils

import kotlin.math.roundToInt

class CalculatorEngine {
    private var display = "0"
    private var previousValue = 0.0
    private var currentOperation: Char? = null
    private var shouldResetDisplay = false

    fun getDisplay(): String = display

    fun appendDigit(digit: Char) {
        if (shouldResetDisplay) {
            display = digit.toString()
            shouldResetDisplay = false
        } else {
            if (display == "0" && digit != '.') {
                display = digit.toString()
            } else if (digit == '.' && !display.contains('.')) {
                display += digit
            } else if (digit != '.') {
                display += digit
            }
        }
    }

    fun setOperation(op: Char) {
        val currentValue = display.toDoubleOrNull() ?: 0.0

        if (currentOperation != null && !shouldResetDisplay) {
            previousValue = performOperation(previousValue, currentValue, currentOperation!!)
            display = formatResult(previousValue)
        } else {
            previousValue = currentValue
        }

        currentOperation = op
        shouldResetDisplay = true
    }

    fun equals(): String {
        if (currentOperation == null) return display

        val currentValue = display.toDoubleOrNull() ?: 0.0
        previousValue = performOperation(previousValue, currentValue, currentOperation!!)
        display = formatResult(previousValue)
        currentOperation = null
        shouldResetDisplay = true

        return display
    }

    fun percent(): String {
        val current = display.toDoubleOrNull() ?: 0.0
        val result = if (previousValue != 0.0 && currentOperation != null) {
            (previousValue / 100.0) * current
        } else {
            current / 100.0
        }
        display = formatResult(result)
        return display
    }

    fun backspace() {
        if (display.isNotEmpty()) {
            display = if (display.length == 1) {
                "0"
            } else {
                display.dropLast(1)
            }
        }
    }

    fun clear() {
        display = "0"
        previousValue = 0.0
        currentOperation = null
        shouldResetDisplay = false
    }

    private fun performOperation(
        prev: Double,
        current: Double,
        operation: Char
    ): Double = when (operation) {
        '+' -> prev + current
        '-' -> prev - current
        '*' -> prev * current
        '/' -> if (current != 0.0) prev / current else 0.0
        else -> current
    }

    private fun formatResult(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            val rounded = (value * 1000000).roundToInt() / 1000000.0
            rounded.toString().trimEnd('0').trimEnd('.')
        }
    }
}
