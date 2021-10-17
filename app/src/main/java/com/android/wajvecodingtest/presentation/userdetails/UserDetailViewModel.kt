package com.android.wajvecodingtest.presentation.userdetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.wajvecodingtest.domain.entity.UserDetails
import com.android.wajvecodingtest.domain.usecase.GetUserDetailsUseCase
import com.android.wajvecodingtest.util.Resource
import com.android.wajvecodingtest.util.Variables
import kotlinx.coroutines.launch

/**To store & manage UI-related data in a lifecycle conscious way!
 * this class allows data to survive configuration changes such as screen rotation
 * by interacting with [GetUserDetailsUseCase]
 *
 * */
class UserDetailViewModel @ViewModelInject constructor(private val getUserDetailsUseCase: GetUserDetailsUseCase) : ViewModel() {

    val userDetailReceivedLiveData = MutableLiveData<Resource<UserDetails?>>()
    val retryClickedLiveData = MutableLiveData<Boolean>()
    val isLoad = MutableLiveData<Boolean>()

    init {
        isLoad.value = false
    }

    fun loadUserDetails(uid: String?){
        viewModelScope.launch {
            try {
                userDetailReceivedLiveData.postValue(Resource.loading(null))
                if (Variables.isNetworkConnected) {

                    getUserDetailsUseCase.saveUid(uid)
                    getUserDetailsUseCase.getUserDetailData().let { events ->
                            isLoad.value = true
                            userDetailReceivedLiveData.postValue(Resource.success(events))

                    }
                }
                else {
                    isLoad.value = true
                    userDetailReceivedLiveData.postValue(Resource.error("No Internet Connection", null))
                }

            } catch (exception: Exception) {
                isLoad.value = true
                userDetailReceivedLiveData.postValue(Resource.error(exception.toString(), null))
            }
        }
    }

    fun onButtonRetryClicked() {
        isLoad.value = false
        retryClickedLiveData.value=true
    }

}