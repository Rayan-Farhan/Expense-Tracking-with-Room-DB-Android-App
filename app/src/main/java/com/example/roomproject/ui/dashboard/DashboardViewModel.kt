package com.example.roomproject.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.roomproject.data.local.ExpenseDatabase
import com.example.roomproject.data.repository.ExpenseRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository

    init {
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
    }

    // Grab the total spent from the repository and convert it to LiveData
    val totalSpent = repository.totalSpent.asLiveData()
}