package com.example.roomproject.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomproject.R
import com.example.roomproject.databinding.FragmentHistoryBinding
import com.example.roomproject.ui.adapter.ExpenseAdapter

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHistoryBinding.bind(view)

        val adapter = ExpenseAdapter { expenseToDelete ->
            viewModel.delete(expenseToDelete)
        }

        binding.recyclerviewExpenses.adapter = adapter
        binding.recyclerviewExpenses.layoutManager = LinearLayoutManager(requireContext())

        // Setup a Floating Action Button to navigate to the Add Expense screen
        binding.fabAddExpense.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_addExpenseFragment)
        }

        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            adapter.submitList(expenses)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}