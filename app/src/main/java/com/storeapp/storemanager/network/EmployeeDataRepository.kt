package com.storeapp.storemanager.network

import com.storeapp.storemanager.model.BaseEntity
import com.storeapp.storemanager.model.employee.EmployeeItem
import io.reactivex.Observable

class EmployeeDataRepository {

    private val employeeApi: EmployeeApi = ServiceFactory.createRetrofitService(
        EmployeeApi::class.java,
        EmployeeApi.SERVICE_ENDPOINT
    )


    fun getEmployeeList(): Observable<BaseEntity<List<EmployeeItem?>>> {
        return employeeApi.getEmployeeList()
    }

    fun getEmployee(id: Int): Observable<BaseEntity<EmployeeItem?>> {
        return employeeApi.getEmployee(id)
    }

    fun deleteEmployee(id: Int): Observable<BaseEntity<EmployeeItem?>> {
        return employeeApi.deleteEmployee(id)
    }

}