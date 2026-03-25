package com.animecalc.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.animecalc.app.data.HistoryEntry
import com.animecalc.app.data.HistoryManager
import com.animecalc.app.data.PreferencesManager
import com.animecalc.app.utils.CalculatorEngine

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {
    private val engine = CalculatorEngine()
    private val historyManager = HistoryManager(application)
    private val preferencesManager = PreferencesManager(application)

    private val _displayText = MutableLiveData<String>("0")
    val displayText: LiveData<String> = _displayText

    private val _history = MutableLiveData<List<HistoryEntry>>()
    val history: LiveData<List<HistoryEntry>> = _history

    private val _isDarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    private var lastExpression = ""

    init {
        refreshHistory()
        _isDarkMode.value = preferencesManager.isDarkMode()
    }

    fun appendDigit(digit: Char) {
        engine.appendDigit(digit)
        updateDisplay()
    }

    fun setOperation(op: Char) {
        lastExpression = engine.getDisplay()
        engine.setOperation(op)
        updateDisplay()
    }

    fun equals() {
        val result = engine.equals()
        val expression = "$lastExpression"
        historyManager.addEntry(expression, result)
        refreshHistory()
        updateDisplay()
    }

    fun percent() {
        engine.percent()
        updateDisplay()
    }

    fun backspace() {
        engine.backspace()
        updateDisplay()
    }

    fun clear() {
        engine.clear()
        lastExpression = ""
        updateDisplay()
    }

    fun toggleDarkMode() {
        preferencesManager.toggleDarkMode()
        _isDarkMode.value = preferencesManager.isDarkMode()
    }

    fun clearHistory() {
        historyManager.clearHistory()
        refreshHistory()
    }

    fun useHistoryItem(entry: HistoryEntry) {
        clear()
        engine.appendDigit('0')
        for (char in entry.result) {
            if (char.isDigit() || char == '.' || char == '-') {
                engine.appendDigit(char)
            }
        }
        engine.backspace()
        for (char in entry.result) {
            if (char.isDigit() || char == '.' || char == '-') {
                engine.appendDigit(char)
            }
        }
        updateDisplay()
    }

    private fun refreshHistory() {
        _history.value = historyManager.getHistory()
    }

    private fun updateDisplay() {
        _displayText.value = engine.getDisplay()
    }
}
