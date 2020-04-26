package com.storeapp.storemanager.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.storeapp.storemanager.database.EmployeeDatabaseDao

@Suppress("UNCHECKED_CAST")
class EmployeeListViewModelFactory(
    private val dataSource: EmployeeDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeListViewModel::class.java)) {
            return EmployeeListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}