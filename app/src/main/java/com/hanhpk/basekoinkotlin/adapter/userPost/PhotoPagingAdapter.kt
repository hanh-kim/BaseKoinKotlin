package com.hanhpk.basekoinkotlin.adapter.userPost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hanhpk.basekoinkotlin.api.responses.UserPostResponse
import com.hanhpk.basekoinkotlin.databinding.ItemPhotoBinding
import com.hanhpk.basekoinkotlin.databinding.ItemUserPostBinding
import com.hanhpk.basekoinkotlin.model.Photo

class PhotoPagingAdapter : PagingDataAdapter<Photo, PhotoPagingAdapter.ViewHolder>(diffCallBack) {

    private var onItemClick: ( Int,Photo)->Unit = {_,_->}

    inner class ViewHolder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let {
                    onItemClick(bindingAdapterPosition,it)
                }
            }
        }

        fun bind(item:Photo){
            when(binding){
                is ItemPhotoBinding->{
                    binding.model = item
                    binding.executePendingBindings()
                }
                else->{}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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

private val diffCallBack = object: DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}