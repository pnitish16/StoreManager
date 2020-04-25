package com.storeapp.storemanager.model.employee

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class EmployeeItem(

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("employee_name")
	val employeeName: String? = null,

	@field:SerializedName("employee_salary")
	val employeeSalary: String? = null,

	@field:SerializedName("id")
	val id: Int? = 0,

	@field:SerializedName("employee_age")
	val employeeAge: Int? = 0
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readValue(Int::class.java.classLoader) as? Int
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(profileImage)
		parcel.writeString(employeeName)
		parcel.writeString(employeeSalary)
		parcel.writeValue(id)
		parcel.writeValue(employeeAge)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<EmployeeItem> {
		override fun createFromParcel(parcel: Parcel): EmployeeItem {
			return EmployeeItem(parcel)
		}

		override fun newArray(size: Int): Array<EmployeeItem?> {
			return arrayOfNulls(size)
		}
	}
}