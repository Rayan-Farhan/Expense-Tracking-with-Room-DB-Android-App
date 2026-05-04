package com.example.roomproject.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.roomproject.R
import com.example.roomproject.databinding.FragmentDashboardBinding
import com.example.roomproject.databinding.FragmentHistoryBinding

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Initialize the ViewModel (No factory needed!)
    private val viewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDashboardBinding.bind(view)

        // Observe the total spent
        viewModel.totalSpent.observe(viewLifecycleOwner) { total ->
            // If the database is empty, 'total' will be null. We default to 0.0
            val displayTotal = total ?: 0.0

            // Format the text to show 2 decimal places (e.g., $45.50)
            binding.tvTotalSpent.text = String.format("$%.2f", displayTotal)
        }

        // Setup a Floating Action Button to navigate to the Add Expense screen
        binding.fabAddExpense.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addExpenseFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}