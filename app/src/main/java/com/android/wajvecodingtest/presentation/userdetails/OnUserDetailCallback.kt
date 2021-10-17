package com.android.wajvecodingtest.presentation.userdetails

/**
 * To make an interaction between [MainActivity] & its children
 * */
interface OnUserDetailCallback {

    fun navigateToUserDetailPage(uid: String)

}