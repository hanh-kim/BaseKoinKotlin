package com.hanhpk.basekoinkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hanhpk.basekoinkotlin.BR
import com.hanhpk.basekoinkotlin.base.BaseItem

open class BaseRecyclerViewPagingAdapter <T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var clickListener: ClickListener? = null
    private var listItems =  ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return BaseViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model: BaseItem = getItems(position) as BaseItem
        val baseViewHolder: BaseViewHolder = holder as BaseViewHolder
        baseViewHolder.bind(model)
        baseViewHolder.viewDataBinding.root.setOnClickListener {
            clickListener?.onClickItem(position, baseViewHolder.viewDataBinding.root)
        }
    }

    fun setItems(listItems: ArrayList<T>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    fun addItems(listItems: ArrayList<T>){
        val size = this.listItems.size
        this.listItems.addAll(listItems)
        notifyItemRangeInserted(size,this.listItems.size)
    }

    fun removeLastItem() {
        if (this.listItems.size > 0) {
            this.listItems.removeAt(itemCount - 1)
            notifyItemRemoved(itemCount)
        }
    }

    override fun getItemCount() = listItems.size

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position)
    }

    private fun getLayoutId(position: Int): Int {
        val model: BaseItem = listItems[position] as BaseItem
        return model.layoutResourceId
    }

    open fun getItems(position: Int): T {
        return listItems[position]
    }

    fun getListItems()  = listItems

    class BaseViewHolder(var viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
        viewDataBinding.root
    ) {
        fun bind(model: BaseItem?) {
            viewDataBinding.setVariable(BR.model, model)
            viewDataBinding.executePendingBindings()
        }
    }

    open fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener!!
    }

    interface ClickListener {
        fun onClickItem(position: Int, view: View?)
    }

    private var disableLoadMore = false
    private var isLoading = false
    var loadingMoreListener: () -> Unit = {}

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        var firstVisibleItemPosition = 0
                        var lastVisibleItemPosition = 0

                        if (disableLoadMore || isLoading) {
                            return
                        }
                        val layoutManager = recyclerView.layoutManager
                        if (layoutManager is LinearLayoutManager) {
                            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                            lastVisibleItemPosition =
                                layoutManager.findLastCompletelyVisibleItemPosition()
                        } else if (layoutManager is GridLayoutManager) {
                            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                            lastVisibleItemPosition =
                                layoutManager.findLastCompletelyVisibleItemPosition()
                        }
                        if (firstVisibleItemPosition > 0 && lastVisibleItemPosition == itemCount - 1) {
                            isLoading = true
                            loadingMoreListener()
                        }
                    }
                    else -> {
                    }
                }
            }
        })
    }

    fun setIsLoading(isLoading: Boolean){
        this.isLoading = isLoading
    }
    fun enableLoadingMore(enable: Boolean) {
        this.disableLoadMore = !enable
    }
}