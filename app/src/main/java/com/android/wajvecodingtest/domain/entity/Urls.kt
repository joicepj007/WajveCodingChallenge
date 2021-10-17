package com.android.wajvecodingtest.domain.entity

import com.google.gson.annotations.SerializedName

data class Urls(
    @SerializedName("public_profile")
    var publicProfile: String
)
