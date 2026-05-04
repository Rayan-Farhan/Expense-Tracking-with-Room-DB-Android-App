/*
Main access point to local storage!
The companion object ensures that only one instance of the database is ever created (a Singleton pattern).
Prevents memory leaks and data corruption.
 */

package com.example.roomproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.AutoMigration

// Configuration for DB
@Database(
    entities = [Expense::class], // Makes Expense.kt a table in SQLite
    version = 2, // Used for migrations later
    exportSchema = true, // Saves the save schema history as files
    autoMigrations = [
        AutoMigration(from = 1 , to = 2)
    ]
)

abstract class ExpenseDatabase : RoomDatabase() { // abstract so we will not be directly creating an instance of this class, instead room will do for us
    abstract fun expenseDao(): ExpenseDao
    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null // singleton so one DB instance (safe + efficient)

        fun getDatabase(context: Context): ExpenseDatabase {
            // if instance exists, return. Else, create it
            return INSTANCE ?: synchronized(this) { // synchronized means only ONE thread creates DB so no crash
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Avoid memory leaks
                    ExpenseDatabase::class.java,
                    "expense_database"
                ).build() // create DB instance
                INSTANCE = instance
                return instance
            }
        }
    }
}