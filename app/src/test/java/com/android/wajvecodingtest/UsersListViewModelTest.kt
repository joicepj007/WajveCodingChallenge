package com.android.wajvecodingtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.wajvecodingtest.domain.entity.LinkedinUsers
import com.android.wajvecodingtest.domain.usecase.GetLinkedinUsersUseCase
import com.android.wajvecodingtest.presentation.users.UsersListViewModel
import com.android.wajvecodingtest.util.Resource
import com.android.wajvecodingtest.util.Variables
import com.android.wajvecodingtest.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UsersListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getLinkedinUsersUseCase: GetLinkedinUsersUseCase


    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<LinkedinUsers>?>>

    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<LinkedinUsers>())
                .`when`(getLinkedinUsersUseCase)
                .getLinkedinUsersListData()
            Variables.isNetworkConnected=true
            val viewModel = UsersListViewModel(getLinkedinUsersUseCase)
            viewModel.loadUsersList()
            viewModel.usersListReceivedLiveData.observeForever(apiUsersObserver)
            Mockito.verify(getLinkedinUsersUseCase).getLinkedinUsersListData()
            Mockito.verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            viewModel.usersListReceivedLiveData.removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(getLinkedinUsersUseCase)
                .getLinkedinUsersListData()
            Variables.isNetworkConnected=true
            val viewModel = UsersListViewModel(getLinkedinUsersUseCase)
            viewModel.loadUsersList()
            viewModel.usersListReceivedLiveData.observeForever(apiUsersObserver)
            Mockito.verify(getLinkedinUsersUseCase).getLinkedinUsersListData()
            Mockito.verify(apiUsersObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.usersListReceivedLiveData.removeObserver(apiUsersObserver)
        }
    }



}