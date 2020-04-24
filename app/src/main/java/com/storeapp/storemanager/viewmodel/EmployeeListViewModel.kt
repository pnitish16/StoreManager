package com.storeapp.storemanager.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.storeapp.storemanager.model.employee.EmployeeItem
import com.storeapp.storemanager.network.EmployeeDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EmployeeListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var employeeDataRepository: EmployeeDataRepository = EmployeeDataRepository()
    var mutableEmployeeList = MutableLiveData<List<EmployeeItem?>>()
    var mutableResponseError = MutableLiveData<String>()
    var mutableEmployee = MutableLiveData<EmployeeItem?>()

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
    fun getEmployee(id: String) {
        employeeDataRepository.getEmployee(id).observeOn(AndroidSchedulers.mainThread())
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

    //delete single employee
    @SuppressLint("CheckResult")
    fun deleteEmployee(id: String) {
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
}