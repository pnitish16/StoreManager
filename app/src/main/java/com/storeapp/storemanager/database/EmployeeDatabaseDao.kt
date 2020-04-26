package com.storeapp.storemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.storeapp.storemanager.model.employee.EmployeeItem

@Dao
interface EmployeeDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllEmployees(employees: List<EmployeeItem?>)

    @Update
    fun updateEmployee(employeeItem: EmployeeItem)

    @Query("SELECT * FROM employee_table ORDER BY id ASC")
    fun getAllEmployees(): LiveData<List<EmployeeItem?>>

    @Query("DELETE FROM employee_table WHERE id=:empId")
    fun deleteEmployee(empId: Int)
}
