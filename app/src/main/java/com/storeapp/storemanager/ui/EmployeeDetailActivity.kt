package com.storeapp.storemanager.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.storeapp.storemanager.NetworkConnectionUtils
import com.storeapp.storemanager.R
import com.storeapp.storemanager.adapter.EmployeeListAdapter
import com.storeapp.storemanager.database.EmployeeDatabase
import com.storeapp.storemanager.model.employee.EmployeeItem
import com.storeapp.storemanager.viewmodel.EmployeeListViewModel
import com.storeapp.storemanager.viewmodel.EmployeeListViewModelFactory
import kotlinx.android.synthetic.main.activity_employee_detail.*

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var employeeListViewModel: EmployeeListViewModel
    private var employeeItem: EmployeeItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        initView()
    }

    private fun initView() {
        if (intent.extras != null) {
            val bundle = intent.getBundleExtra("employee_data")
            if (bundle != null) {
                employeeItem = bundle.getParcelable("employee_item")
            }
        }

        title = employeeItem?.employeeName
        val application = requireNotNull(this).application
        val dataSource = EmployeeDatabase.getInstance(application).employeeDatabaseDao
        val viewModelFactory = EmployeeListViewModelFactory(dataSource)
        employeeListViewModel =
            ViewModelProvider(this, viewModelFactory).get(EmployeeListViewModel::class.java)

        val employeeId = employeeItem?.id ?: 0
        if (employeeId != 0) {
            if (NetworkConnectionUtils.isNetworkConnected(this)) {
                pbEmployeeDetail.visibility = View.VISIBLE
                employeeListViewModel.getEmployee(employeeId)
                subscribeViewModel()
            }else{
                tvEmployeeDetailNameValue.text = employeeItem?.employeeName
                tvEmployeeDetailSalaryValue.text = employeeItem?.employeeSalary
                tvEmployeeDetailAgeValue.text = "${employeeItem?.employeeAge}"
            }
        }
    }

    private fun subscribeViewModel() {
        employeeListViewModel.mutableEmployee.observe(this,
            Observer<EmployeeItem?> {
                pbEmployeeDetail.visibility = View.GONE
                tvEmployeeDetailNameValue.text = it?.employeeName
                tvEmployeeDetailSalaryValue.text = it?.employeeSalary
                tvEmployeeDetailAgeValue.text = "${it?.employeeAge}"
            })

        employeeListViewModel.mutableResponseError.observe(this,
            Observer<String> {
                pbEmployeeDetail.visibility = View.GONE
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                tvEmployeeDetailNameValue.text = employeeItem?.employeeName?:"unknown"
                tvEmployeeDetailSalaryValue.text = employeeItem?.employeeSalary?:"NA"
                tvEmployeeDetailAgeValue.text = "${employeeItem?.employeeAge?:"unknown"}"
            })
    }
}
