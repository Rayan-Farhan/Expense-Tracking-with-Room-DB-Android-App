package com.example.roomproject.ui.add_expense
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.data.repository.ExpenseRepository
import com.example.roomproject.data.local.Expense
import com.example.roomproject.data.local.ExpenseDatabase
import kotlinx.coroutines.launch

class AddExpenseViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: ExpenseRepository
    init {
        // Grab the database using the built-in application context
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    fun insert(amount: Double, category: String, note: String, date: Long, paymentMethod: String) {
        val newExpense = Expense(amount = amount, category = category, note = note, date = date, paymentMethod = paymentMethod)
        viewModelScope.launch {
            repository.insert(newExpense)
        }
    }
}