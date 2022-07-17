package com.hanhpk.basekoinkotlin.adapter.userPost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hanhpk.basekoinkotlin.adapter.BaseRecyclerViewAdapter
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.databinding.ItemUserPostBinding

class UserPostAdapter : BaseRecyclerViewAdapter<UserPostResponse>() {

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
                getItems(bindingAdapterPosition)?.let {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItems(position)
        item.let {
            (holder as ViewHolder).bind(it)
        }
    }

}
