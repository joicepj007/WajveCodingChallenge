package com.android.wajvecodingtest.data

import com.android.wajvecodingtest.domain.entity.LinkedinUsers
import com.android.wajvecodingtest.domain.entity.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("dev/random/list")
    suspend fun getLinkedinUserList(): List<LinkedinUsers>

    @GET("dev/random")
    suspend fun getUserDetail(@Query("uid") uid: String?): UserDetails
}