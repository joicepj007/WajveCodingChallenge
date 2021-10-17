package com.android.wajvecodingtest.domain.entity

import com.google.gson.annotations.SerializedName

data class Extra(
    @SerializedName("raw_info")
    var rawInfo: RawInfo?
)
