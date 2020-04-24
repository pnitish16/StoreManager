package com.storeapp.storemanager.model.employee

import com.google.gson.annotations.SerializedName

data class EmployeeItem(

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("employee_name")
	val employeeName: String? = null,

	@field:SerializedName("employee_salary")
	val employeeSalary: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("employee_age")
	val employeeAge: String? = null
)