package com.example.roomproject.ui.add_expense

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.roomproject.R
import com.example.roomproject.databinding.FragmentAddExpenseBinding

class AddExpenseFragment : Fragment(R.layout.fragment_add_expense) {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    // Uses the Factory to get the ViewModel with the repository attached
    private val viewModel: AddExpenseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddExpenseBinding.bind(view)

        binding.btnSave.setOnClickListener {
            val amount = binding.etAmount.text.toString().toDoubleOrNull() ?: 0.0
            val category = binding.etCategory.text.toString()
            val note = binding.etNote.text.toString()
            val date = System.currentTimeMillis() // Save current time
            val paymentMethod = "Cash"

            viewModel.insert(amount, category, note, date, paymentMethod)

            // Navigate back to the previous screen once saved
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}