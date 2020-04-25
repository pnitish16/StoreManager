package com.storeapp.storemanager.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
    EmployeeListAdapter.OnListFragmentInteractionListener, View.OnClickListener {

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

        btnEmployeeNameSort.setOnClickListener(this)
        btnEmployeeAgeSort.setOnClickListener(this)

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

        employeeListViewModel.mutableEmployeeListName.observe(this, Observer<List<EmployeeItem?>> {
            employeeList.clear()
            employeeList.addAll(it)
            employeeListAdapter.notifyDataSetChanged()
        })
    }

    //region recyclerview interactions
    override fun onListFragmentInteraction(item: EmployeeItem) {
        val employeeDetailIntent = Intent(this, EmployeeDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("employee_item", item)
        employeeDetailIntent.putExtra("employee_data", bundle)
        startActivity(employeeDetailIntent)
    }

    override fun onDeleteEmployee(employeeId: Int) {
        if (employeeId != 0) {
            val deleteDialog = deleteDialog(employeeId).apply { } ?: return
            deleteDialog.show()
        }
    }
    //endregion

    override fun onClick(p0: View?) {
        val view = p0.apply { } ?: return
        when (view.id) {
            R.id.btnEmployeeNameSort -> {
                employeeListViewModel.sortListByName(employeeList)
            }
            R.id.btnEmployeeAgeSort -> {
                employeeListViewModel.sortListByAge(employeeList)
            }
        }
    }


    //region show delete dialog
    private fun deleteDialog(employeeId: Int): AlertDialog? {
        return AlertDialog.Builder(this) // set message, title, and icon
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete?")
            .setIcon(android.R.drawable.ic_delete)
            .setPositiveButton("delete",
                DialogInterface.OnClickListener { dialog, _ ->
                    employeeListViewModel.deleteEmployee(employeeId)
                })
            .setNegativeButton("cancel",
                DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
            .create()
    }
    //endregion
}
