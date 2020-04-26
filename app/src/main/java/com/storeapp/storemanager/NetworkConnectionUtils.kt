package com.storeapp.storemanager

import android.content.Context
import android.net.ConnectivityManager

object NetworkConnectionUtils {
    fun isNetworkConnected(context: Context): Boolean {
        val ConnectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}