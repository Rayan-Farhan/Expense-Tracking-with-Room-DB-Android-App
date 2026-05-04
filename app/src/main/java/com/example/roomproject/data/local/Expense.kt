/* This is your data model.
The @Entity annotation tells Room to create an SQLite table named "expenses" with these columns.
*/
package com.example.roomproject.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val date: Long,
    val category: String,
    val note: String,
    val paymentMethod: String? = null
)