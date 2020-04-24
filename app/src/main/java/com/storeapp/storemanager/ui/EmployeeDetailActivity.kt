package com.storeapp.storemanager.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.storeapp.storemanager.R
import com.storeapp.storemanager.adapter.EmployeeListAdapter
import com.storeapp.storemanager.model.employee.EmployeeItem
import com.storeapp.storemanager.viewmodel.EmployeeListViewModel
import com.storeapp.storemanager.viewmodel.EmployeeListViewModelFactory
import kotlinx.android.synthetic.main.activity_employee_detail.*

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var employeeListViewModel: EmployeeListViewModel
    private var employeeId: String? = null
    private var employeeName: String? = "Unknown"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        initView()
    }

    private fun initView() {
        if (intent.extras != null) {
            val bundle = intent.getBundleExtra("employee_data")
            if (bundle != null) {
                employeeId = bundle.getString("employee_id")
                employeeName = bundle.getString("employee_name")
            }
        }

        title = employeeName
        val application = requireNotNull(this).application
        val viewModelFactory = EmployeeListViewModelFactory(application)
        employeeListViewModel =
            ViewModelProvider(this, viewModelFactory).get(EmployeeListViewModel::class.java)

        val empId = employeeId.apply { } ?: "id"
        if (empId != "id") {
            pbEmployeeDetail.visibility = View.VISIBLE
            employeeListViewModel.getEmployee(empId)
        }
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        employeeListViewModel.mutableEmployee.observe(this,
            Observer<EmployeeItem?> {
                pbEmployeeDetail.visibility = View.GONE
                tvEmployeeDetailNameValue.text = it?.employeeName
                tvEmployeeDetailSalaryValue.text = it?.employeeSalary
                tvEmployeeDetailAgeValue.text = it?.employeeAge
            })

        employeeListViewModel.mutableResponseError.observe(this,
            Observer<String> {
                pbEmployeeDetail.visibility = View.GONE
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
    }
}
