package com.android.wajvecodingtest.domain.repository

import com.android.wajvecodingtest.domain.UserDetails

/**
 * To make an interaction between [UserDetailRepository] & [GetUserDetailUseCase]
 * */
interface UserDetailRepository {

    suspend fun getUserDetail(): List<UserDetails>
}