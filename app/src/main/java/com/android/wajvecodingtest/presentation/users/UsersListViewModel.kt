package com.android.wajvecodingtest.presentation.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.wajvecodingtest.domain.entity.LinkedinUsers
import com.android.wajvecodingtest.domain.usecase.GetLinkedinUsersUseCase
import com.android.wajvecodingtest.util.Resource
import com.android.wajvecodingtest.util.Variables
import kotlinx.coroutines.launch

/**To store & manage UI-related data in a lifecycle conscious way!
 * this class allows data to survive configuration changes such as screen rotation
 * by interacting with [GetLinkedinUsersUseCase]
 *
 * */
class UsersListViewModel @ViewModelInject constructor(private val getLinkedinUsersUseCase: GetLinkedinUsersUseCase) :
    ViewModel() {

    val usersListReceivedLiveData = MutableLiveData<Resource<List<LinkedinUsers>?>>()
    val retryClickedLiveData = MutableLiveData<Boolean>()
    val isLoad = MutableLiveData<Boolean>()

    init {
        isLoad.value = false
    }

    fun loadUsersList() {
        viewModelScope.launch {
            try {
                usersListReceivedLiveData.postValue(Resource.loading(null))
                if (Variables.isNetworkConnected) {
                    getLinkedinUsersUseCase.getLinkedinUsersListData().let { events ->
                        isLoad.value = true
                        usersListReceivedLiveData.postValue(Resource.success(events))

                    }
                } else {
                    isLoad.value = true
                    usersListReceivedLiveData.postValue(
                        Resource.error(
                            "No Internet Connection",
                            null
                        )
                    )
                }
            } catch (exception: Exception) {
                isLoad.value = true
                usersListReceivedLiveData.postValue(Resource.error(exception.toString(), null))
            }
        }
    }

    fun onButtonRetryClicked() {
        isLoad.value = false
        retryClickedLiveData.value = true
    }
}