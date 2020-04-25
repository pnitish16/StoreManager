package com.storeapp.storemanager.network

import com.storeapp.storemanager.model.BaseEntity
import com.storeapp.storemanager.model.employee.EmployeeItem
import io.reactivex.Observable
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {

    @GET("employees")
    fun getEmployeeList(): Observable<BaseEntity<List<EmployeeItem?>>>

    @GET("employee/{id}")
    fun getEmployee(@Path("id") id: Int): Observable<BaseEntity<EmployeeItem?>>

    @DELETE("delete/{id}")
    fun deleteEmployee(@Path("id") id: Int): Observable<BaseEntity<EmployeeItem?>>

    companion object {
        const val SERVICE_ENDPOINT = "http://dummy.restapiexample.com/api/v1/"
    }
}