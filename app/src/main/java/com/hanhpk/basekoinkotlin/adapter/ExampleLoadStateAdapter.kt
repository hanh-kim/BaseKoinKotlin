package com.hanhpk.basekoinkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hanhpk.basekoinkotlin.R
import com.hanhpk.basekoinkotlin.databinding.ItemLoadStateBinding

class ExampleLoadStateAdapter : LoadStateAdapter<ExampleLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state, parent, false)
    ) {
        private val binding = ItemLoadStateBinding.bind(itemView)
        private val progressBar = binding.loadingProgressBar

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {

            }
            Glide.with(progressBar.context).load(R.raw.load_anim_gif).into(progressBar)
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}