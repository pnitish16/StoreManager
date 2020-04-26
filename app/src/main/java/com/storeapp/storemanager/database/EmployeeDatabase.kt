package com.storeapp.storemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.storeapp.storemanager.model.employee.EmployeeItem

@Database(entities = [EmployeeItem::class], version = 2, exportSchema = false)
abstract class EmployeeDatabase : RoomDatabase() {

    abstract val employeeDatabaseDao: EmployeeDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getInstance(context: Context): EmployeeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}