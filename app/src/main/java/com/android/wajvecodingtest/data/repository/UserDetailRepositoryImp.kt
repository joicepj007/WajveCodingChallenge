package com.android.wajvecodingtest.data.repository

import com.android.wajvecodingtest.data.RetrofitService
import com.android.wajvecodingtest.domain.UserDetails
import com.android.wajvecodingtest.domain.repository.UserDetailRepository

/**
 * This repository is responsible for
 * fetching data[UserDetail] from server
 * */

class UserDetailRepositoryImp(
    private val retrofitService: RetrofitService
) :
    UserDetailRepository {
    override suspend fun getUserDetail(uid: String?): List<UserDetails> {
        return retrofitService.getUserDetail(uid)
    }
}