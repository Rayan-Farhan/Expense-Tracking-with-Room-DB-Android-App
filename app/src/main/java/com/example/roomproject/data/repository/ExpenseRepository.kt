/*
The repository abstracts access to multiple data sources.
It is a suggested best practice for code separation and architecture.
While we only have Room right now, if we later added a remote server, the ViewModel would still just call
repository.insert(), and the Repository would decide whether to save it locally, remotely, or both.
 */

package com.example.roomproject.data.repository

import com.example.roomproject.data.local.Expense
import com.example.roomproject.data.local.ExpenseDao
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()
    val totalSpent: Flow<Double?> = expenseDao.getTotalSpent()

    suspend fun insert(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
}