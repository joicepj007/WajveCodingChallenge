package com.android.wajvecodingtest.domain.repository

import com.android.wajvecodingtest.domain.entity.UserDetails

/**
 * To make an interaction between [UserDetailRepository] & [GetUserDetailUseCase]
 * */
interface UserDetailRepository {

    suspend fun getUserDetail(uid:String?): UserDetails
}