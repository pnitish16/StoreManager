package com.storeapp.storemanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.storeapp.storemanager.R
import com.storeapp.storemanager.adapter.EmployeeListAdapter
import com.storeapp.storemanager.model.employee.EmployeeItem
import com.storeapp.storemanager.viewmodel.EmployeeListViewModel
import com.storeapp.storemanager.viewmodel.EmployeeListViewModelFactory
import kotlinx.android.synthetic.main.activity_employee_list.*

class EmployeeListActivity : AppCompatActivity(),
    EmployeeListAdapter.OnListFragmentInteractionListener {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var employeeListAdapter: EmployeeListAdapter
    private lateinit var employeeListViewModel: EmployeeListViewModel
    private var employeeList = mutableListOf<EmployeeItem?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)
        initView()
    }

    private fun initView() {
        val application = requireNotNull(this).application
        val viewModelFactory = EmployeeListViewModelFactory(application)
        employeeListViewModel =
            ViewModelProvider(this, viewModelFactory).get(EmployeeListViewModel::class.java)
        layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        employeeListAdapter = EmployeeListAdapter(this, employeeList)
        rvEmployeeList.layoutManager = layoutManager
        rvEmployeeList.adapter = employeeListAdapter

        title = "EmployeeList"
        pbEmployeeList.visibility = View.VISIBLE
        employeeListViewModel.getEmployeeList()
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        employeeListViewModel.mutableEmployeeList.observe(this,
            Observer<List<EmployeeItem?>> {
                pbEmployeeList.visibility = View.GONE
                if (it.isNotEmpty())
                    tvEmptyViewEmployeeList.visibility = View.GONE
                else
                    tvEmptyViewEmployeeList.visibility = View.VISIBLE
                employeeList.clear()
                employeeList.addAll(it)
                employeeListAdapter.notifyDataSetChanged()
            })

        employeeListViewModel.mutableResponseError.observe(this,
            Observer<String> {
                pbEmployeeList.visibility = View.GONE
                tvEmptyViewEmployeeList.visibility = View.VISIBLE
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
    }


    override fun onListFragmentInteraction(item: EmployeeItem) {
    }
}
