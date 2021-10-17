package com.android.wajvecodingtest.domain

import com.google.gson.annotations.SerializedName

data class UserDetailsInfo(
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("firstName")
    var first_name: String,
    @SerializedName("headline")
    var headline: String,
    @SerializedName("industry")
    var industry: String,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("nickname")
    var nickname: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("location")
    var location: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("urls")
    var urls: Urls,
)
