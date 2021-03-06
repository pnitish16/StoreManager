package com.storeapp.storemanager.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.storeapp.storemanager.database.EmployeeDatabase
import com.storeapp.storemanager.database.EmployeeDatabaseDao
import com.storeapp.storemanager.model.BaseEntity
import com.storeapp.storemanager.model.employee.EmployeeItem
import com.storeapp.storemanager.network.EmployeeApi
import com.storeapp.storemanager.network.EmployeeDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Response

class EmployeeListViewModel(val database: EmployeeDatabaseDao) : ViewModel() {

    private var employeeDataRepository: EmployeeDataRepository = EmployeeDataRepository()
    var mutableEmployeeList = MutableLiveData<List<EmployeeItem?>>()
    var mutableEmployeeListDb = MutableLiveData<List<EmployeeItem?>>()
    var mutableResponseError = MutableLiveData<String>()
    var mutableEmployee = MutableLiveData<EmployeeItem?>()
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    var mutableEmployeeListName = MutableLiveData<List<EmployeeItem?>>()

    //coroutine database operations
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //region Database operations
    init {
//        intializeEmployeeList()
    }

    fun getEmployeeListFromDb(): LiveData<List<EmployeeItem?>> {
        return database.getAllEmployees()
    }

    suspend fun insertEmployeeDb(employeeList: List<EmployeeItem?>) {
        withContext(Dispatchers.IO) {
            database.insertAllEmployees(employeeList)
        }
    }

    suspend fun deleteEmployeeDb(id: Int) {
        withContext(Dispatchers.IO) {
            database.deleteEmployee(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

//endregion


    //getting all employees
    @SuppressLint("CheckResult")
    fun getEmployeeList() {
        employeeDataRepository.getEmployeeList().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                val status = it.status
                if (status.equals("success")) {
                    if (it.data != null) {
                        mutableEmployeeList.value = it.data!!
                    }
                } else if (status == "failed") {
                    mutableResponseError.value = it.message!!
                }
            },
                { error ->
                    run {
                        Log.d("error", error.message ?: "Error")
                        mutableResponseError.value = error.message ?: "Error"
                    }
                })
    }

    //getting single employe
    @SuppressLint("CheckResult")
    fun getEmployee(id: Int) {
        employeeDataRepository.getEmployee(id).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
//            .retry(3)
            .subscribe({
                val status = it.status
                if (status.equals("success")) {
                    if (it.data != null) {
                        mutableEmployee.value = it.data!!
                    }
                } else if (status == "failed") {
                    mutableResponseError.value = it.message!!
                }
            },
                { error ->
                    run {
                        Log.d("error", error.message ?: "Error")
                        mutableResponseError.value = error.message ?: "Error"
                    }
                })
    }

    //delete single employee
    @SuppressLint("CheckResult")
    fun deleteEmployee(id: Int) {
        employeeDataRepository.deleteEmployee(id).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                val status = it.status
                if (status.equals("success")) {
                    if (it.data != null) {
                        mutableEmployee.value = it.data!!
                    }
                } else if (status == "failed") {
                    mutableResponseError.value = it.message!!
                }
            },
                { error ->
                    run {
                        Log.d("error", error.message ?: "Error")
                        mutableResponseError.value = error.message ?: "Error"
                    }
                })
    }


    //region sorting list by name and age functions
    fun sortListByName(list: MutableList<EmployeeItem?>) {
        viewModelScope.launch {
            mutableEmployeeListName.value = sortListName(list)
        }
    }

    fun sortListByAge(list: MutableList<EmployeeItem?>) {
        viewModelScope.launch {
            mutableEmployeeListName.value = sortListAge(list)
        }
    }
//endregion

    /**
     * Sorting the list
     */
    suspend fun sortListName(employeeList: MutableList<EmployeeItem?>): List<EmployeeItem?> =
        withContext(Dispatchers.Default) {
            return@withContext employeeList
                .sortedBy { it?.employeeName }
        }


    suspend fun sortListAge(employeeList: MutableList<EmployeeItem?>): List<EmployeeItem?> =
        withContext(Dispatchers.Default) {
            return@withContext employeeList
                .sortedBy { it?.employeeAge }
        }
}