package com.hanhpk.basekoinkotlin.adapter.userPost

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.databinding.ItemUserPostBinding

class UserPostPagingAdapter : PagingDataAdapter<UserPostResponse, UserPostPagingAdapter.ViewHolder>(diffCallBack) {

    interface OnItemClickListener {
        fun onItemClick(position: Int,item: UserPostResponse)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let {
                    listener?.onItemClick(bindingAdapterPosition,it)
                }
            }
        }

        fun bind(item:UserPostResponse){
            when(binding){
                is ItemUserPostBinding->{
                    binding.model = item
                    binding.executePendingBindings()
                }
                else->{}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
       val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

}

private val diffCallBack = object: DiffUtil.ItemCallback<UserPostResponse>() {
    override fun areItemsTheSame(oldItem: UserPostResponse, newItem: UserPostResponse): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: UserPostResponse, newItem: UserPostResponse): Boolean = oldItem == newItem
}