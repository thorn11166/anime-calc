package com.animecalc.app.data

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

data class HistoryEntry(
    val expression: String,
    val result: String,
    val timestamp: Long
)

class HistoryManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "calculator_history",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val HISTORY_COUNT = 10
        private const val HISTORY_PREFIX = "history_"
        private const val HISTORY_SIZE_KEY = "history_size"
    }

    fun addEntry(expression: String, result: String) {
        val currentSize = getHistorySize()
        val newSize = minOf(currentSize + 1, HISTORY_COUNT)

        // Shift entries
        for (i in (newSize - 1) downTo 1) {
            val prevEntry = getEntry(i - 1)
            if (prevEntry != null) {
                saveEntry(i, prevEntry)
            }
        }

        // Add new entry at front
        val newEntry = HistoryEntry(expression, result, System.currentTimeMillis())
        saveEntry(0, newEntry)

        prefs.edit().putInt(HISTORY_SIZE_KEY, newSize).apply()
    }

    fun getHistory(): List<HistoryEntry> {
        val size = getHistorySize()
        return (0 until size).mapNotNull { getEntry(it) }
    }

    fun clearHistory() {
        val size = getHistorySize()
        for (i in 0 until size) {
            deleteEntry(i)
        }
        prefs.edit().putInt(HISTORY_SIZE_KEY, 0).apply()
    }

    private fun getHistorySize(): Int {
        return prefs.getInt(HISTORY_SIZE_KEY, 0)
    }

    private fun saveEntry(index: Int, entry: HistoryEntry) {
        val key = "$HISTORY_PREFIX$index"
        val combined = "${entry.expression}|${entry.result}|${entry.timestamp}"
        prefs.edit().putString(key, combined).apply()
    }

    private fun getEntry(index: Int): HistoryEntry? {
        val key = "$HISTORY_PREFIX$index"
        val combined = prefs.getString(key, null) ?: return null
        val parts = combined.split("|")
        return if (parts.size == 3) {
            HistoryEntry(parts[0], parts[1], parts[2].toLongOrNull() ?: 0L)
        } else null
    }

    private fun deleteEntry(index: Int) {
        val key = "$HISTORY_PREFIX$index"
        prefs.edit().remove(key).apply()
    }

    fun formatTimestamp(timestamp: Long): String {
        val cal = Calendar.getInstance().apply { timeInMillis = timestamp }
        val today = Calendar.getInstance()

        return when {
            isSameDay(cal, today) -> {
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
            }
            isYesterday(cal, today) -> {
                "Yesterday"
            }
            else -> {
                SimpleDateFormat("MM/dd", Locale.getDefault()).format(Date(timestamp))
            }
        }
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun isYesterday(cal1: Calendar, cal2: Calendar): Boolean {
        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }
        return cal1.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)
    }
}
