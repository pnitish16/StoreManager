package com.storeapp.storemanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.storeapp.storemanager.R
import com.storeapp.storemanager.model.employee.EmployeeItem
import com.storeapp.storemanager.ui.EmployeeListActivity
import kotlinx.android.synthetic.main.list_item_employee.view.*

class EmployeeListAdapter(
    private val context: Context,
    private val mValues: List<EmployeeItem?>
) : RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_employee, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        val item1 = item.apply { } ?: return
        holder.tvEmployeeName.text = item1.employeeName

        val urlImage1 = item1.profileImage.apply { } ?: ""
        if (urlImage1.isNotEmpty()) {
            Glide.with(holder.ivEmployeeProfile)
                .load(urlImage1)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivEmployeeProfile)
        }

        holder.mView.setOnClickListener { (context as EmployeeListActivity).onListFragmentInteraction(item1) }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val ivEmployeeProfile: ImageView = mView.ivEmployeeProfile
        val tvEmployeeName: TextView = mView.tvEmployeeName
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: EmployeeItem)
    }
}
