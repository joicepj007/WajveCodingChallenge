package com.android.wajvecodingtest.presentation.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.wajvecodingtest.R
import com.android.wajvecodingtest.databinding.FragmentListUsersBinding
import com.android.wajvecodingtest.domain.entity.LinkedinUsers
import com.android.wajvecodingtest.presentation.userdetails.OnUserDetailCallback
import com.android.wajvecodingtest.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment : Fragment(), OnUserListAdapterListener {

    private lateinit var fragmentListUsersBinding: FragmentListUsersBinding
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var mCallback: OnUserDetailCallback? = null
    private var adapter: UsersListAdapter? = null
    private var toolbar: Toolbar? = null
    private val viewModel: UsersListViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnUserDetailCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnUserDetailCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = UsersListAdapter(this)
        viewModel.loadUsersList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentListUsersBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_users, container, false)
        fragmentListUsersBinding.userListViewModel = viewModel
        fragmentListUsersBinding.manufacturerRecyclerView.adapter = adapter

        toolbar = fragmentListUsersBinding.root.findViewById(R.id.toolbar) as Toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.title = getString(R.string.str_users)

        //** Set the Layout Manager of the RecyclerView
        setRVLayoutManager()

        setupObservers()

        return fragmentListUsersBinding.root
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(requireContext())
        fragmentListUsersBinding.manufacturerRecyclerView.layoutManager = mLayoutManager
        fragmentListUsersBinding.manufacturerRecyclerView.setHasFixedSize(true)
        fragmentListUsersBinding.manufacturerRecyclerView.itemAnimator = null
    }

    private fun setupObservers() {
        viewModel.usersListReceivedLiveData.observe(viewLifecycleOwner, {
            it?.let { response ->
                if (response.status == Status.SUCCESS) {
                    response.data?.let {
                        initRecyclerView(it)
                    }
                } else if (response.status == Status.ERROR) {
                    if (response.message.equals(getString(R.string.str_no_internet))) {
                        viewModel.isLoad.observe(viewLifecycleOwner, { load ->
                            load?.let { visibility ->
                                fragmentListUsersBinding.progressBar.visibility =
                                    if (visibility) View.GONE else View.VISIBLE
                                fragmentListUsersBinding.retry.visibility = View.VISIBLE
                                fragmentListUsersBinding.manufacturerRecyclerView.visibility =
                                    View.GONE
                                fragmentListUsersBinding.noNetwork.visibility = View.VISIBLE
                                fragmentListUsersBinding.textinputError.visibility = View.GONE
                            }
                        })
                    } else {
                        viewModel.isLoad.observe(viewLifecycleOwner, { load ->
                            load?.let { visibility ->
                                fragmentListUsersBinding.progressBar.visibility =
                                    if (visibility) View.GONE else View.VISIBLE
                                fragmentListUsersBinding.retry.visibility = View.VISIBLE
                                fragmentListUsersBinding.textinputError.visibility =
                                    View.VISIBLE
                                fragmentListUsersBinding.manufacturerRecyclerView.visibility =
                                    View.GONE
                                fragmentListUsersBinding.noNetwork.visibility = View.GONE
                                fragmentListUsersBinding.textinputError.text =
                                    response.message.toString()
                            }
                        })
                    }
                } else if (response.status == Status.LOADING) {
                    viewModel.isLoad.observe(viewLifecycleOwner, { load ->
                        load?.let { visibility ->
                            fragmentListUsersBinding.progressBar.visibility =
                                if (visibility) View.GONE else View.VISIBLE
                            fragmentListUsersBinding.manufacturerRecyclerView.visibility =
                                View.VISIBLE
                            fragmentListUsersBinding.noNetwork.visibility = View.GONE
                            fragmentListUsersBinding.textinputError.visibility = View.GONE
                            fragmentListUsersBinding.retry.visibility = View.GONE
                        }
                    })
                }
            }
        })


        viewModel.retryClickedLiveData.observe(viewLifecycleOwner, {
            it?.let { clicked ->
                if (clicked) {
                    if (fragmentListUsersBinding.retry.isVisible) {
                        viewModel.loadUsersList()
                    }
                }
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        adapter = null
        mCallback = null
    }

    companion object {

        val FRAGMENT_NAME: String = UsersListFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            UsersListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun initRecyclerView(userList: List<LinkedinUsers>) {
        adapter?.updateData(userList)
    }

    override fun showUsersList(uid: String) {
        mCallback?.navigateToUserDetailPage(uid)
    }
}