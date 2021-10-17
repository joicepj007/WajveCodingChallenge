package com.android.wajvecodingtest.data.repository

import com.android.wajvecodingtest.data.RetrofitService
import com.android.wajvecodingtest.domain.entity.UserDetails
import com.android.wajvecodingtest.domain.repository.UserDetailRepository
import retrofit2.Response

/**
 * This repository is responsible for
 * fetching data[UserDetail] from server
 * */

class UserDetailRepositoryImp(
    private val retrofitService: RetrofitService
) :
    UserDetailRepository {
    override suspend fun getUserDetail(uid: String?): UserDetails {
        return retrofitService.getUserDetail(uid)
    }
}