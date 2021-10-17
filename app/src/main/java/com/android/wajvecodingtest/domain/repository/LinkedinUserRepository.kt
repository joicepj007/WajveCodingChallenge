package com.android.wajvecodingtest.domain.repository

import com.android.wajvecodingtest.domain.entity.LinkedinUsers
import retrofit2.Response

/**
 * To make an interaction between [LinkedinUserRepository] & [GetLinkedinUserUseCase]
 * */
interface LinkedinUserRepository {

    suspend fun getLinkedinUserList(): List<LinkedinUsers>
}