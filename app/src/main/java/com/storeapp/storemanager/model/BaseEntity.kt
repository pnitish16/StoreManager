package com.storeapp.storemanager.model

import com.google.gson.annotations.SerializedName

class BaseEntity<T> {
    var status: String? = null
    var data: T? = null
    var message: String? = null
}