package com.android.wajvecodingtest.presentation.users

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.wajvecodingtest.R
import com.android.wajvecodingtest.databinding.HolderItemBinding
import com.android.wajvecodingtest.domain.entity.LinkedinUsers
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.holder_item.view.*
import java.util.ArrayList

internal class UsersListAdapter(val mListener: OnUserListAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var userList: List<LinkedinUsers> = ArrayList()
    private val picasso = Picasso.get()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderAlbumBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_item, parent, false
        )
        return EventViewHolder(holderAlbumBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).onBind(getItem(position), position)
    }

    private fun getItem(position: Int): LinkedinUsers {
        return userList[position]
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateData(users: List<LinkedinUsers>) {
        val diffCallback = UsersDiffCallBack(users, userList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        userList = users.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    inner class EventViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun onBind(userInfo: LinkedinUsers, position: Int) {
            val holderUserBinding = dataBinding as HolderItemBinding
            holderUserBinding.linkedinUsersInfo = userInfo

            picasso.load(userInfo.info?.pictureUrl).into(itemView.profile_pic)
            itemView.setOnClickListener {
                mListener.showUsersList(userInfo.uid)
            }
        }
    }
}
