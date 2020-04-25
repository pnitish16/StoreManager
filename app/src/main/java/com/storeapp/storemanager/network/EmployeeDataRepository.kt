package com.storeapp.storemanager.network

import com.storeapp.storemanager.model.BaseEntity
import com.storeapp.storemanager.model.employee.EmployeeItem
import io.reactivex.Observable

class EmployeeDataRepository {

    fun getEmployeeList(): Observable<BaseEntity<List<EmployeeItem?>>> {
        val service =
            ServiceFactory.createRetrofitService(EmployeeApi::class.java, EmployeeApi.SERVICE_ENDPOINT)
        return service.getEmployeeList()
    }

    fun getEmployee(id: Int): Observable<BaseEntity<EmployeeItem?>> {
        val service =
            ServiceFactory.createRetrofitService(EmployeeApi::class.java, EmployeeApi.SERVICE_ENDPOINT)
        return service.getEmployee(id)
    }

    fun deleteEmployee(id : Int): Observable<BaseEntity<EmployeeItem?>> {
        val service =
            ServiceFactory.createRetrofitService(EmployeeApi::class.java, EmployeeApi.SERVICE_ENDPOINT)
        return service.deleteEmployee(id)
    }

}