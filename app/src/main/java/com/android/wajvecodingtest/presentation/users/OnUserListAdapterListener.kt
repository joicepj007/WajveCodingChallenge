package com.android.wajvecodingtest.presentation.users

/**
 * To make an interaction between [UsersListAdapter] & [UsersListFragment]
 * */
interface OnUserListAdapterListener {

    fun showUsersList(uid: String)
}