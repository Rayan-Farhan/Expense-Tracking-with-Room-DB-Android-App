package com.example.roomproject.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomproject.data.local.Expense
import com.example.roomproject.data.local.ExpenseDatabase
import com.example.roomproject.data.repository.ExpenseRepository
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExpenseRepository

    init {
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }
    val allExpenses = repository.allExpenses.asLiveData()

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }
}