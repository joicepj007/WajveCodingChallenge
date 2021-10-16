package com.android.wajvecodingtest.data

import com.android.wajvecodingtest.domain.LinkedinUsers
import com.android.wajvecodingtest.domain.UserDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("dev/random/list")
    suspend fun getLinkedinUserList(): List<LinkedinUsers>

    @GET("dev/random")
    suspend fun getUserDetail(@Query("uid") uid: String?): List<UserDetails>
}