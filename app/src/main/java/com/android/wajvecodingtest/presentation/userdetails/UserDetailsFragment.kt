package com.android.wajvecodingtest.presentation.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.wajvecodingtest.R
import com.android.wajvecodingtest.databinding.FragmentUserDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import com.android.wajvecodingtest.util.Status

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private lateinit var fragmentUserDetailsBinding: FragmentUserDetailsBinding
    private var uid: String? = null
    private var profileUrl: String? = null
    private var toolbar: Toolbar? = null
    private val viewModel: UserDetailViewModel by viewModels()
    private val picasso = Picasso.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        uid = arguments?.getString(KEY_UID)
        viewModel.loadUserDetails(uid)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentUserDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        fragmentUserDetailsBinding.userDetailViewModel = viewModel
        fragmentUserDetailsBinding.lifecycleOwner = viewLifecycleOwner

        toolbar = fragmentUserDetailsBinding.root.findViewById(R.id.toolbar) as Toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar?.setNavigationIcon(R.mipmap.ic_back)

        setupObservers()

        toolbar?.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }

        return fragmentUserDetailsBinding.root
    }

    private fun setupObservers() {
        viewModel.userDetailReceivedLiveData.observe(viewLifecycleOwner, {
            it?.let { response ->
                if (response.status == Status.SUCCESS) {
                    response.data?.let { userdata ->
                        userdata.info.let { info ->
                            info?.urls.let { url ->
                                profileUrl = url?.publicProfile
                            }
                        }
                        userdata.extra.let { extra ->
                            extra?.rawInfo.let { rawinfo ->
                                fragmentUserDetailsBinding.mainContent.visibility = View.VISIBLE
                                fragmentUserDetailsBinding.retry.visibility = View.GONE
                                fragmentUserDetailsBinding.noNetwork.visibility = View.GONE
                                fragmentUserDetailsBinding.textinputError.visibility = View.GONE
                                picasso.load(rawinfo?.coverPictureUrl)
                                    .into(fragmentUserDetailsBinding.coverImage)
                                picasso.load(rawinfo?.pictureUrl)
                                    .into(fragmentUserDetailsBinding.profilePic)
                            }
                        }
                    }
                } else if (response.status == Status.ERROR) {
                    if (response.message.equals(getString(R.string.str_no_internet))) {
                        viewModel.isLoad.observe(viewLifecycleOwner, { load ->
                            load?.let { visibility ->
                                fragmentUserDetailsBinding.progressBar.visibility =
                                    if (visibility) View.GONE else View.VISIBLE
                                fragmentUserDetailsBinding.retry.visibility = View.VISIBLE
                                fragmentUserDetailsBinding.noNetwork.visibility = View.VISIBLE
                                fragmentUserDetailsBinding.textinputError.visibility = View.GONE
                            }
                        })
                    } else {
                        viewModel.isLoad.observe(viewLifecycleOwner, { load ->
                            load?.let { visibility ->
                                fragmentUserDetailsBinding.progressBar.visibility =
                                    if (visibility) View.GONE else View.VISIBLE
                                fragmentUserDetailsBinding.retry.visibility = View.VISIBLE
                                fragmentUserDetailsBinding.textinputError.visibility =
                                    View.VISIBLE
                                fragmentUserDetailsBinding.noNetwork.visibility = View.GONE
                                fragmentUserDetailsBinding.textinputError.text =
                                    response.message.toString()
                            }
                        })
                    }
                } else if (response.status == Status.LOADING) {
                    viewModel.isLoad.observe(viewLifecycleOwner, { load ->
                        load?.let { visibility ->
                            fragmentUserDetailsBinding.progressBar.visibility =
                                if (visibility) View.GONE else View.VISIBLE
                            fragmentUserDetailsBinding.noNetwork.visibility = View.GONE
                            fragmentUserDetailsBinding.textinputError.visibility = View.GONE
                            fragmentUserDetailsBinding.retry.visibility = View.GONE
                        }
                    })
                }
            }
        })

        viewModel.retryClickedLiveData.observe(viewLifecycleOwner, {
            it?.let { clicked ->
                if (clicked) {
                    if (fragmentUserDetailsBinding.retry.isVisible) {
                        viewModel.loadUserDetails(uid)
                    }
                }
            }
        })
    }

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_scrolling, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //handle item clicks of menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get item id to handle item clicks
        val id = item.itemId
        //handle item clicks
        if (id == R.id.action_share) {
            profileUrl?.run {
                val theUrl = this
                val urlstr: Uri = Uri.parse(theUrl)
                val urlintent = Intent()
                urlintent.data = urlstr
                urlintent.action = Intent.ACTION_VIEW
                startActivity(urlintent)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY_UID = "KEY_UID"
        val FRAGMENT_NAME: String = UserDetailsFragment::class.java.name

        @JvmStatic
        fun newInstance(uid: String) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_UID, uid)
                }
            }
    }
}