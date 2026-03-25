package com.animecalc.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.animecalc.app.data.HistoryManager
import com.animecalc.app.databinding.FragmentHistoryBinding
import com.animecalc.app.viewmodel.CalculatorViewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculatorViewModel by activityViewModels()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyManager: HistoryManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyManager = HistoryManager(requireContext())
        historyAdapter = HistoryAdapter(historyManager) { entry ->
            viewModel.useHistoryItem(entry)
        }

        binding.historyRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

        binding.btnClearHistory.setOnClickListener {
            animateButtonPress(it)
            viewModel.clearHistory()
        }

        viewModel.history.observe(viewLifecycleOwner) { entries ->
            historyAdapter.setItems(entries)
            binding.emptyState.visibility = if (entries.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
