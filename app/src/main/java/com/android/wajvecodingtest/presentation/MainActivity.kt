package com.android.wajvecodingtest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.wajvecodingtest.R
import com.android.wajvecodingtest.presentation.userdetails.OnUserDetailCallback
import com.android.wajvecodingtest.presentation.userdetails.UserDetailsFragment
import com.android.wajvecodingtest.presentation.users.UsersListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnUserDetailCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateToUserListPage()
    }

    private fun navigateToUserListPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_container,
                UsersListFragment.newInstance(),
                UsersListFragment.FRAGMENT_NAME
            ).commit()
    }

    override fun navigateToUserDetailPage(uid: String) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_container,
                UserDetailsFragment.newInstance(uid),
                UserDetailsFragment.FRAGMENT_NAME
            )
            .addToBackStack(UserDetailsFragment.FRAGMENT_NAME)
            .commitAllowingStateLoss()

    }
}