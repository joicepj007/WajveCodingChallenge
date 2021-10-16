package com.android.wajvecodingtest.domain

data class UserDetails(
    var provider: String,
    var uid: String,
    var info: UserDetailsInfo?
)
