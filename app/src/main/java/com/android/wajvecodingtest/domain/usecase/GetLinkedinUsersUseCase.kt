package com.android.wajvecodingtest.domain.usecase


import com.android.wajvecodingtest.domain.LinkedinUsers
import com.android.wajvecodingtest.domain.repository.LinkedinUserRepository
import javax.inject.Inject

/**
 * An interactor that calls the actual implementation of [EventListViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetLinkedinUsersUseCase @Inject constructor(private val repository: LinkedinUserRepository) {

    suspend fun getLinkedinUsersListData(): List<LinkedinUsers> {
        return repository.getLinkedinUserList()
    }
}