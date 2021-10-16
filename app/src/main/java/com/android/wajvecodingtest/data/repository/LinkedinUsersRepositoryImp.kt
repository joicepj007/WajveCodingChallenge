package com.android.wajvecodingtest.data.repository
import com.android.wajvecodingtest.data.RetrofitService
import com.android.wajvecodingtest.domain.LinkedinUsers
import com.android.wajvecodingtest.domain.repository.LinkedinUserRepository

/**
 * This repository is responsible for
 * fetching data[LinkedinUsers] from server
 * */

class LinkedinUsersRepositoryImp(
    private val retrofitService: RetrofitService
) :
    LinkedinUserRepository {
    override suspend fun getLinkedinUserList(): List<LinkedinUsers> {
        return retrofitService.getLinkedinUserList()
    }
}