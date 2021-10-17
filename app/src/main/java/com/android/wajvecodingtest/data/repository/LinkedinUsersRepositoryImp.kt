package com.android.wajvecodingtest.data.repository
import com.android.wajvecodingtest.data.RetrofitService
import com.android.wajvecodingtest.domain.entity.LinkedinUsers
import com.android.wajvecodingtest.domain.repository.LinkedinUserRepository
import retrofit2.Response

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