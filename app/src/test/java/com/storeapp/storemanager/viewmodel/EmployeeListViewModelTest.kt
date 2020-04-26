package com.storeapp.storemanager.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.google.common.base.Verify.verify
import com.storeapp.storemanager.RxImmediateSchedulerRule
import com.storeapp.storemanager.model.BaseEntity
import com.storeapp.storemanager.model.employee.EmployeeItem
import com.storeapp.storemanager.network.EmployeeDataRepository
import io.reactivex.Observable
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.delay
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class EmployeeListViewModelTest {

    // Subject under test
    private lateinit var employeeListViewModel: EmployeeListViewModel
    @Mock
    private lateinit var successObserver: Observer<List<EmployeeItem?>>

    @Mock
    private lateinit var failureObserver: Observer<String>
    @Mock
    private lateinit var lifecycleOwner: LifecycleOwner
    var lifecycle: Lifecycle? = null

    private lateinit var employeeDataRepository: EmployeeDataRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Before
    fun setupViewModel() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        employeeDataRepository = EmployeeDataRepository()
        employeeListViewModel = EmployeeListViewModel()
        employeeListViewModel.mutableEmployeeList.observeForever(successObserver)
    }

    @Test
    fun testEmployeeListSuccess() {
        employeeListViewModel.getEmployeeList()
        employeeListViewModel.mutableEmployeeList.observeForever(successObserver)
        assertNotNull(employeeListViewModel.mutableEmployeeList.value)
        assert(employeeListViewModel.mutableEmployeeList.value!!.isNotEmpty())
    }

    @Test
    fun testEmployeeListError() {
        employeeListViewModel.getEmployeeList()
        employeeListViewModel.mutableResponseError.observeForever(failureObserver)
        assertNotNull(employeeListViewModel.mutableResponseError.value)
        assert(employeeListViewModel.mutableResponseError.value!!.isNotEmpty())
    }
}