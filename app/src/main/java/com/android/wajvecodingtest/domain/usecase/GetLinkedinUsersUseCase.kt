package com.android.wajvecodingtest.domain.usecase


import com.android.wajvecodingtest.domain.entity.LinkedinUsers
import com.android.wajvecodingtest.domain.repository.LinkedinUserRepository
import javax.inject.Inject

/**
 * An interactor that calls the actual implementation of [UsersListViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetLinkedinUsersUseCase @Inject constructor(private val repository: LinkedinUserRepository) {

    suspend fun getLinkedinUsersListData(): List<LinkedinUsers> {
        return repository.getLinkedinUserList()
    }
}