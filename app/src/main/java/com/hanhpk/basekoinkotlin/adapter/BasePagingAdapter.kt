package com.dmm.ptown.views.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmm.ptown.BR
import com.dmm.ptown.models.BaseItem
import com.dmm.ptown.utils.onAvoidDoubleClick

class BasePagingAdapter<T : BaseItem> :
    PagingDataAdapter<T, BasePagingAdapter.BaseViewHolder>(object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.itemKey == newItem.itemKey

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem == newItem
    }) {
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.viewDataBinding.root.onAvoidDoubleClick {
            getItem(position)?.let { it1 -> itemCLickListener(position, it1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return BaseViewHolder(viewDataBinding)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position)
    }

    private fun getLayoutId(position: Int): Int {
        val model: BaseItem = getItem(position) as BaseItem
        return model.layoutResourceId
    }

    class BaseViewHolder(var viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
        viewDataBinding.root
    ) {
        fun bind(model: BaseItem?) {
            viewDataBinding.setVariable(BR.model, model)
            viewDataBinding.executePendingBindings()
        }
    }

    var itemCLickListener: (position: Int, item: T) -> Unit = { _: Int, _: T ->  }
}