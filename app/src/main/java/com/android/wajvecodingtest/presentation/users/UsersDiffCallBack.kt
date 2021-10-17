package com.android.wajvecodingtest.presentation.users


import androidx.recyclerview.widget.DiffUtil
import com.android.wajvecodingtest.domain.entity.LinkedinUsers

class UsersDiffCallBack(
    private val newItems: List<LinkedinUsers>,
    private val oldItems: List<LinkedinUsers>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return (oldItem.uid == newItem.uid) && (oldItem.info.let { it?.name } == newItem.info.let { it?.name })
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem == newItem
    }
}