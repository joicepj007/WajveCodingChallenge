package com.android.wajvecodingtest.domain.repository

import com.android.wajvecodingtest.domain.LinkedinUsers

/**
 * To make an interaction between [LinkedinUserRepository] & [GetLinkedinUserUseCase]
 * */
interface LinkedinUserRepository {

    suspend fun getLinkedinUserList(): List<LinkedinUsers>
}