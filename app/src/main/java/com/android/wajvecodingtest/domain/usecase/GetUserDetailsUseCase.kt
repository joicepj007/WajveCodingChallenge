package com.android.wajvecodingtest.domain.usecase

import com.android.wajvecodingtest.domain.UserDetails
import com.android.wajvecodingtest.domain.repository.UserDetailRepository
import javax.inject.Inject

/**
 * An interactor that calls the actual implementation of [CarTypesViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetUserDetailsUseCase @Inject constructor(private val repository: UserDetailRepository) {

    private var uid: String? = null

    fun saveUid(key: String?) {
        uid = key
    }

    suspend fun getUserDetailData(uid: String?): UserDetails {
        return repository.getUserDetail(uid)
    }
}