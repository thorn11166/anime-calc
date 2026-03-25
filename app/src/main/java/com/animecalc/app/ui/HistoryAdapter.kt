package com.animecalc.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.animecalc.app.data.HistoryEntry
import com.animecalc.app.data.HistoryManager
import com.animecalc.app.databinding.ItemHistoryBinding

class HistoryAdapter(
    private val historyManager: HistoryManager,
    private val onItemClick: (HistoryEntry) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var items: List<HistoryEntry> = emptyList()

    fun setItems(newItems: List<HistoryEntry>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: HistoryEntry) {
            binding.apply {
                expressionText.text = entry.expression
                resultText.text = entry.result
                timestampText.text = historyManager.formatTimestamp(entry.timestamp)

                root.setOnClickListener {
                    onItemClick(entry)
                }
            }
        }
    }
}
