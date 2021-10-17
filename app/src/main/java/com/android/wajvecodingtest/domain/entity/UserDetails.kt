package com.android.wajvecodingtest.domain.entity

data class UserDetails(
    var provider: String,
    var uid: String,
    var info: UserDetailsInfo?,
    var extra: Extra?,
)
